package com.postbank.applicantaptitude.config;

import com.postbank.applicantaptitude.entity.Applicant;
import com.postbank.applicantaptitude.repository.ApplicantRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class IdNumberAuthenticationProvider implements AuthenticationProvider {

    private final ApplicantRepository applicantRepository;

    public IdNumberAuthenticationProvider(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String idNumber = authentication.getName();

        // Check if the user exists in the database
        Optional<Applicant> applicantOptional = applicantRepository.findById(idNumber);

        if (applicantOptional.isPresent()) {
            Applicant applicant = applicantOptional.get();

            // Check if user has completed the test
            if (applicant.isHasCompletedTest()) {
                throw new RuntimeException("You have already attempted the test.");
            }

            // Auth successful - return auth token
            return new UsernamePasswordAuthenticationToken(idNumber, null, Collections.emptyList());
        }

        throw new RuntimeException("Invalid ID Number");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}