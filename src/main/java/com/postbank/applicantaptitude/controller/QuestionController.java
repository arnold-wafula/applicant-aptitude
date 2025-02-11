package com.postbank.applicantaptitude.controller;

import com.postbank.applicantaptitude.dto.UserResponseDTO;
import com.postbank.applicantaptitude.entity.Question;
import com.postbank.applicantaptitude.entity.UserResponse;
import com.postbank.applicantaptitude.repository.QuestionRepository;
import com.postbank.applicantaptitude.repository.UserResponseRepository;
import com.postbank.applicantaptitude.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*") // Allow frontend to access API
public class QuestionController {

    private final QuestionService questionService;
    private final UserResponseRepository userResponseRepository;
    private final QuestionRepository questionRepository;

    public QuestionController(QuestionService questionService, UserResponseRepository userResponseRepository, QuestionRepository questionRepository) {
        this.questionService = questionService;
        this.userResponseRepository = userResponseRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> submitAnswers(@RequestBody List<UserResponseDTO> responses) {
        if (responses.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "No answers submitted."));
        }

        String userId = responses.get(0).getUserId(); // Assuming all answers belong to the same user

        // Check if user has already attempted the test
        if (userResponseRepository.existsByUserId(userId)) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "You have already attempted the test."));
        }

        int totalQuestions = responses.size();
        int correctAnswers = 0;

        for (UserResponseDTO responseDTO : responses) {
            Question question = questionRepository.findById(responseDTO.getQuestionId()).orElse(null);
            if (question != null) {
                String answer = responseDTO.getAnswer() == null ? "" : responseDTO.getAnswer();
                if (answer.equalsIgnoreCase(String.valueOf(question.getCorrectOption()))) {
                    correctAnswers++;
                }
                UserResponse response = new UserResponse(userId, question, answer);
                userResponseRepository.save(response);
            }
        }

        // Calculate percentage
        double scorePercentage = ((double) correctAnswers / totalQuestions) * 100;
        boolean passed = scorePercentage >= 80;

        Map<String, String> response = new HashMap<>();
        response.put("message", "Answers submitted successfully!");
        response.put("score", String.format("%.2f", scorePercentage));
        response.put("status", passed ? "pass" : "fail");

        return ResponseEntity.ok(response);
    }
}