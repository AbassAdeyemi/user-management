package com.assessment.demo.usermanagement.repository;

import com.assessment.demo.usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
