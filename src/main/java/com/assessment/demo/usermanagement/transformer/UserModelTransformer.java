package com.assessment.demo.usermanagement.transformer;

import com.assessment.demo.usermanagement.entity.User;
import com.assessment.demo.usermanagement.model.UserRequestModel;
import com.assessment.demo.usermanagement.model.UserResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserModelTransformer {

    private final PasswordEncoder passwordEncoder;

    public User fromRequestModelToUserModel(UserRequestModel requestModel) {
        return User.builder()
                .password(passwordEncoder.encode(requestModel.getPassword()))
                .email(requestModel.getEmail())
                .firstname(requestModel.getFirstname())
                .lastname(requestModel.getLastname())
                .mobile(requestModel.getMobile())
                .role(requestModel.getRole())
                .status(requestModel.getStatus())
                .title(requestModel.getTitle())
                .verified(requestModel.getVerified())
                .dateRegistered(requestModel.getDateRegistered())
                .dateDeactivated(requestModel.getDateDeactivated())
                .build();
    }

    public UserResponseModel fromUserModelToResponseModel(User user) {
        return UserResponseModel.builder()
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .title(user.getTitle())
                .role(user.getRole())
                .status(user.getStatus())
                .mobile(user.getMobile())
                .verified(user.getVerified())
                .dateVerified(user.getDateVerified())
                .dateRegistered(user.getDateRegistered())
                .dateDeactivated(user.getDateDeactivated())
                .build();
    }


}
