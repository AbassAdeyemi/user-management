package com.assessment.demo.usermanagement.entity;

import com.assessment.demo.usermanagement.enumeration.Role;
import com.assessment.demo.usermanagement.enumeration.Status;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String firstname;
    private String lastname;
    private String email;
    private String mobile;
    private String password;
    private Role role;
    private Status status;
    private LocalDate dateRegistered;
    private Boolean verified;
    private LocalDate dateVerified;
    private LocalDate dateDeactivated;
}
