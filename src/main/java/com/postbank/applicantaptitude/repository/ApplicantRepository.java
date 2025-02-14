package com.postbank.applicantaptitude.repository;

import com.postbank.applicantaptitude.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, String> {
    Optional<Applicant> findByIdNumberOrPassport(String idNumberOrPassport);
}