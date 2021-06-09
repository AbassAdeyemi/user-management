package com.assessment.demo.usermanagement.service;

import com.assessment.demo.usermanagement.entity.User;
import com.assessment.demo.usermanagement.enumeration.Status;
import com.assessment.demo.usermanagement.model.UserRequestModel;
import com.assessment.demo.usermanagement.model.UserResponseModel;
import com.assessment.demo.usermanagement.repository.UserRepository;
import com.assessment.demo.usermanagement.transformer.UserModelTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserModelTransformer userModelTransformer;

    @Override
    public UserResponseModel register(UserRequestModel requestModel) {
        User user =  userRepository.save(userModelTransformer.fromRequestModelToUserModel(requestModel));

        return userModelTransformer.fromUserModelToResponseModel(user);

    }

    @Override
    public Page<UserResponseModel> getAll(Pageable pageable) {
        return userRepository.findAllByStatusNot(Status.DEACTIVATED, pageable)
                .map(userModelTransformer::fromUserModelToResponseModel);

    }

    @Override
    public UserResponseModel update(Long id, UserRequestModel requestModel) {
      return userRepository
               .findById(id)
               .map(userModelTransformer::fromUserModelToResponseModel)
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
}
