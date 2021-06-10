package com.assessment.demo.usermanagement.service;

import com.assessment.demo.usermanagement.model.UserRequestModel;
import com.assessment.demo.usermanagement.model.UserResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface UserService {
    UserResponseModel register(UserRequestModel requestModel) throws MessagingException, UnsupportedEncodingException;
    Page<UserResponseModel> getAll(Pageable pageable);
    UserResponseModel update(Long id, UserRequestModel user);
    void delete(Long id);
    void verify(String verificationCode);
}
