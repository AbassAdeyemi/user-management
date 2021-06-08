package com.assessment.demo.usermanagement.controller;

import com.assessment.demo.usermanagement.entity.User;
import com.assessment.demo.usermanagement.model.UserRequestModel;
import com.assessment.demo.usermanagement.model.UserResponseModel;
import com.assessment.demo.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public UserResponseModel register(@RequestBody UserRequestModel requestModel) {
        return userService.register(requestModel);
    }

    @GetMapping("/users")
    public Page<UserResponseModel> get(Pageable pageable) {
        return userService.getAll(pageable);
    }

    @PutMapping("/user")
    public UserResponseModel update(@RequestParam(name = "id") Long id, @RequestBody UserRequestModel requestModel) {
        return userService.update(id, requestModel);
    }

    @DeleteMapping("/user")
    public void delete(Long id) {
        userService.delete(id);
    }
}
