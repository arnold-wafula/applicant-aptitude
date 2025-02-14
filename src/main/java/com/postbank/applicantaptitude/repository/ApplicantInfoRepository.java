package com.postbank.applicantaptitude.repository;

import com.postbank.applicantaptitude.entity.ApplicantInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantInfoRepository extends JpaRepository<ApplicantInfo, Integer> {
    Optional<ApplicantInfo> findByIdNumberOrPassport(String idNumberOrPassport);
}