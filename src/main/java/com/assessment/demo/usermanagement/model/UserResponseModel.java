package com.assessment.demo.usermanagement.model;

import com.assessment.demo.usermanagement.enumeration.Role;
import com.assessment.demo.usermanagement.enumeration.Status;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class UserResponseModel {
    private Long id;
    private String title;
    private String firstname;
    private String lastname;
    private String email;
    private String mobile;
    private Role role;
    private Status status;
    private LocalDate dateRegistered;
    private Boolean verified;
    private LocalDate dateVerified;
    private LocalDate dateDeactivated;
}
