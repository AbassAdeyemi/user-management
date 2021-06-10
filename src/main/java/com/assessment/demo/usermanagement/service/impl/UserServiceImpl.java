package com.assessment.demo.usermanagement.service.impl;

import com.assessment.demo.usermanagement.entity.User;
import com.assessment.demo.usermanagement.enumeration.Status;
import com.assessment.demo.usermanagement.model.UserRequestModel;
import com.assessment.demo.usermanagement.model.UserResponseModel;
import com.assessment.demo.usermanagement.repository.UserRepository;
import com.assessment.demo.usermanagement.service.EmailService;
import com.assessment.demo.usermanagement.service.UserService;
import com.assessment.demo.usermanagement.transformer.UserModelTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${server.servlet.context-path}")
    private String path;

    @Value("${server.port}")
    private int serverPort;


    private  String siteUrL = String.format("http://localhost:%s%s/api/user", serverPort, path);
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public UserResponseModel register(UserRequestModel requestModel) throws MessagingException, UnsupportedEncodingException {

        String randomCode = UUID.randomUUID().toString();
        requestModel.setVerified(false);

        User user =  UserModelTransformer.fromRequestModelToUserModel(requestModel);
        user.setVerificationCode(randomCode);
        user.setDateRegistered(LocalDate.now());
        userRepository.save(user);

        emailService.sendVerificationEmail(user, siteUrL);

        return UserModelTransformer.fromUserModelToResponseModel(user);

    }

    @Override
    public Page<UserResponseModel> getAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserModelTransformer::fromUserModelToResponseModel);

    }

    @Override
    public UserResponseModel update(Long id, UserRequestModel requestModel) {
      return userRepository
               .findById(id)
               .map(user-> {
                    UserModelTransformer.fromRequestModelToUserModel(requestModel);
                   return UserModelTransformer.fromUserModelToResponseModel(userRepository.save(user));
               })
               .orElseThrow(() -> new IllegalArgumentException("User not found with id: "+id));
    }

    @Override
    public void delete(Long id) {
         userRepository
                .findById(id)
                 .map(user -> {
                     user.setStatus(Status.DEACTIVATED);
                     user.setDateDeactivated(LocalDate.now());
                     return userRepository.save(user);
                 }).orElseThrow(() -> new IllegalArgumentException("User cannot be found with id: "+id));
    }

    public void verify(String verificationCode) {
        Optional<User> optionalUser = userRepository.findByVerificationCode(verificationCode);

        if(optionalUser.isEmpty() || optionalUser.get().getVerified())
            return;

            User user = optionalUser.get();
            user.setVerificationCode(null);
            user.setVerified(true);
            userRepository.save(user);



    }
}
