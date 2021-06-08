package com.assessment.demo.usermanagement.service;

import com.assessment.demo.usermanagement.entity.User;
import com.assessment.demo.usermanagement.model.UserRequestModel;
import com.assessment.demo.usermanagement.model.UserResponseModel;

public interface UserService {
    UserResponseModel register(UserRequestModel requestModel);
    UserResponseModel get(Long id);
    UserResponseModel update(Long id, UserRequestModel user);
    void delete(Long id);
}
