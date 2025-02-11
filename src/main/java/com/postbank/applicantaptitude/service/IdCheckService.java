package com.postbank.applicantaptitude.service;

import com.postbank.applicantaptitude.entity.Applicant;
import com.postbank.applicantaptitude.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdCheckService {
    @Autowired
    private ApplicantRepository applicantRepository;

    public IdCheckService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    public Optional<Applicant> findByIdNumber(String idNumber) {
        return applicantRepository.findByIdNumber(idNumber);
    }

    public Applicant saveApplicant(Applicant applicant) {
        return applicantRepository.save(applicant);
    }

    // Method to fetch applicants who haven't completed the test
//    public List<Applicant> findIncompleteApplicants() {
//        return applicantRepository.findByHasCompletedTestFalse();
//    }
}