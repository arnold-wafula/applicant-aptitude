package com.postbank.applicantaptitude.repository;

import com.postbank.applicantaptitude.entity.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserResponseRepository extends JpaRepository<UserResponse, Long> {
    boolean existsByUserId(String userId);

    List<UserResponse> findByUserId(String userId);
}