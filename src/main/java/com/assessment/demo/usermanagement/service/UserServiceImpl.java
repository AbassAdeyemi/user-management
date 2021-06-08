package com.assessment.demo.usermanagement.service;

import com.assessment.demo.usermanagement.entity.User;
import com.assessment.demo.usermanagement.model.UserRequestModel;
import com.assessment.demo.usermanagement.model.UserResponseModel;
import com.assessment.demo.usermanagement.repository.UserRepository;
import com.assessment.demo.usermanagement.transformer.UserModelTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public UserResponseModel get(Long id) {
       User user = userRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("User not found with id: "+id));

       return userModelTransformer.fromUserModelToResponseModel(user);
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
                .ifPresentOrElse(userRepository::delete, ()-> {
                    throw new IllegalArgumentException("User not found with id: "+id);
                });
    }
}
