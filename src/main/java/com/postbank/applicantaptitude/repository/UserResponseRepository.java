package com.postbank.applicantaptitude.repository;

import com.postbank.applicantaptitude.entity.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserResponseRepository extends JpaRepository<UserResponse, Long> {
    boolean existsByUserId(String userId);
}