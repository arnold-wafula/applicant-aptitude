package com.postbank.applicantaptitude.controller;

import com.postbank.applicantaptitude.entity.Applicant;
import com.postbank.applicantaptitude.service.IdCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class IdCheckController {

    @Autowired
    private IdCheckService idCheckService;

    @GetMapping("/validate/{idNumber}")
    public ResponseEntity<Map<String, Object>> validateApplicant(@PathVariable String idNumber) {
        Optional<Applicant> applicant = idCheckService.findByIdNumber(idNumber);

        if (applicant.isPresent()) {
            Applicant existingApplicant = applicant.get();

            // Check if applicant has already completed the test
            if (existingApplicant.isHasCompletedTest()) {
                Map<String, Object> response = new HashMap<>();
                response.put("exists", true);
                response.put("message", "You have already attempted the test.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);  // Return forbidden status
            }

            // If applicant exists and has not completed the test - proceed
            Map<String, Object> response = new HashMap<>();
            response.put("exists", true);
            response.put("message", "Proceed to the test.");
            response.put("redirect", "/instructions?id=" + idNumber);  // Add redirection URL

            return ResponseEntity.status(HttpStatus.OK).body(response);
//            return ResponseEntity
//                    .status(HttpStatus.FOUND)
//                    .header("Location", "/instructions?id=" + idNumber)
//                    .build();

            //response.put("exists", true);
            //response.put("message", "ID Found. You may proceed to the test.");
            //return ResponseEntity.ok(response);
        } else {
            // If applicant does not exist, return an error
            Map<String, Object> response = new HashMap<>();

            response.put("exists", false);
            response.put("message", "ID not found. You cannot proceed.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**@PostMapping("/submit-test")
    public ResponseEntity<Map<String, String>> submitTestResults(@RequestParam String idNumber, @RequestParam int score) {
        Optional<Applicant> applicant = idCheckService.findByIdNumber(idNumber);
        Map<String, String> response = new HashMap<>();

        if (applicant.isPresent()) {
            Applicant existingApplicant = applicant.get();
            existingApplicant.setHasCompletedTest(true);
            idCheckService.saveApplicant(existingApplicant);

            if (score < 60) {
                response.put("message", "Test submitted. Unfortunately, you did not pass.");
            } else {
                response.put("message", "Test submitted. Congratulations, you passed!");
            }

            return ResponseEntity.ok(response);
        } else {
            response.put("message", "ID not found. Test submission failed.");
            return ResponseEntity.status(404).body(response);
        }
    }*/

    // Endpoint to check if a user can access the instructions page based on the idNumber passed
    @GetMapping("/instructions/{idNumber}")
    public ResponseEntity<Map<String, String>> checkInstructionsAccess(@PathVariable String idNumber) {
        Optional<Applicant> applicant = idCheckService.findByIdNumber(idNumber);

        // Check if the applicant exists
        if (!applicant.isPresent()) {
            // If not, redirect them to the index page (home page)
            Map<String, String> response = new HashMap<>();
            response.put("message", "User does not exist.");
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", "/index") // Redirect to index if user does not exist
                    .body(response);
        }

        // Check if the applicant has completed the test
        Applicant existingApplicant = applicant.get();
        if (existingApplicant.isHasCompletedTest()) {
            // If they already completed the test, return an error or redirect
            Map<String, String> response = new HashMap<>();
            response.put("message", "You have already attempted the test.");
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", "/index") // Redirect to index if they have already taken the test
                    .body(response);
        }

        // If the user is valid and hasn't taken the test yet, allow access
        Map<String, String> response = new HashMap<>();
        response.put("message", "Access granted");
        return ResponseEntity.ok(response);
    }
}