package com.assessment.demo.usermanagement.repository;

import com.assessment.demo.usermanagement.entity.User;
import com.assessment.demo.usermanagement.enumeration.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAllByStatusNot(Status status, Pageable pageable);

    Optional<User> findByVerificationCode(String verificationCode);
}
