package com.postbank.applicantaptitude.service;

import com.postbank.applicantaptitude.entity.Applicant;
import com.postbank.applicantaptitude.entity.ApplicantInfo;
import com.postbank.applicantaptitude.repository.ApplicantInfoRepository;
import com.postbank.applicantaptitude.repository.ApplicantRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdCheckService {
    private final ApplicantRepository applicantRepository;
    private final ApplicantInfoRepository applicantInfoRepository;

    public IdCheckService(ApplicantRepository applicantRepository, ApplicantInfoRepository applicantInfoRepository) {
        this.applicantRepository = applicantRepository;
        this.applicantInfoRepository = applicantInfoRepository;
    }

    public Optional<Applicant> findByIdNumber(String idNumberOrPassport) {
        return applicantRepository.findByIdNumberOrPassport(idNumberOrPassport);
    }

    public Applicant saveApplicant(Applicant applicant) {
        return applicantRepository.save(applicant);
    }
}