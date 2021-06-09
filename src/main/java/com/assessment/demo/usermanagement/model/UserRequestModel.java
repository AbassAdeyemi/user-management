package com.assessment.demo.usermanagement.model;

import com.assessment.demo.usermanagement.enumeration.Role;
import com.assessment.demo.usermanagement.enumeration.Status;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter @Setter
public class UserRequestModel {
    private Long id;
    private String title;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @Email
    @NotNull
    private String email;
    @NotNull
    private String mobile;
    @NotNull
    private String password;
    private Role role;
    private Status status;
    private LocalDate dateRegistered;
    private Boolean verified;
    private LocalDate dateVerified;
    private LocalDate dateDeactivated;
}
