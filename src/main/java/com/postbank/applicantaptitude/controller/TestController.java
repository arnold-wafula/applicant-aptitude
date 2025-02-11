package com.postbank.applicantaptitude.controller;

import com.postbank.applicantaptitude.entity.Question;
import com.postbank.applicantaptitude.entity.UserResponse;
import com.postbank.applicantaptitude.repository.QuestionRepository;
import com.postbank.applicantaptitude.repository.UserResponseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController {

    private final QuestionRepository questionRepository;
    private final UserResponseRepository userResponseRepository;

    public TestController(QuestionRepository questionRepository, UserResponseRepository userResponseRepository) {
        this.questionRepository = questionRepository;
        this.userResponseRepository = userResponseRepository;
    }

    @GetMapping
    public String showTestPage(@RequestParam String id, Model model) {
        // Fetch questions in random order
        List<Question> questions = questionRepository.findAll();
        Collections.shuffle(questions); // Randomize order of questions

        model.addAttribute("questions", questions);
        model.addAttribute("idNumber", id);
        return "test";
    }

    @PostMapping("/submit")
    public String submitTest(@RequestParam String id, @RequestParam Map<String, String> answers) {
        if (id == null || id.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is required.");
        }

        if (answers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No answers submitted.");
        }

        // Save user responses
        for (Map.Entry<String, String> entry : answers.entrySet()) {
            try {
                Long questionId = Long.parseLong(entry.getKey().replace("question", ""));
                String answer = entry.getValue();

                // Fetch the Question entity from the database
                Question question = questionRepository.findById(questionId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found: " + questionId));

                // Create a UserResponse entity
                UserResponse response = new UserResponse();
                response.setUserId(id);
                response.setQuestion(question);
                response.setAnswer(answer);

                // Save the response
                userResponseRepository.save(response);
            } catch (NumberFormatException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid question ID format: " + entry.getKey());
            }
        }

        return "redirect:/result?id=" + id;
    }
}