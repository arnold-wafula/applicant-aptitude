package com.postbank.applicantaptitude.controller;

import com.postbank.applicantaptitude.entity.Applicant;
import com.postbank.applicantaptitude.entity.Question;
import com.postbank.applicantaptitude.entity.UserResponse;
import com.postbank.applicantaptitude.repository.QuestionRepository;
import com.postbank.applicantaptitude.repository.UserResponseRepository;
import com.postbank.applicantaptitude.service.IdCheckService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/test")
public class TestController {

    private final QuestionRepository questionRepository;
    private final UserResponseRepository userResponseRepository;
    private final IdCheckService idCheckService;

    public TestController(QuestionRepository questionRepository, UserResponseRepository userResponseRepository, IdCheckService idCheckService) {
        this.questionRepository = questionRepository;
        this.userResponseRepository = userResponseRepository;
        this.idCheckService = idCheckService;
    }

    @GetMapping
    public String showTestPage(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String userId = (String) session.getAttribute("AUTHENTICATED_USER");

        if (userId == null) {
            return "redirect:/"; // Redirect if not logged in
        }

        Optional<Applicant> applicant = idCheckService.findByIdNumber(userId);
        if (applicant.isPresent() && applicant.get().isHasCompletedTest()) {
            // ✅ Fetch and display the user’s score
            //int score = calculateUserScore(userId);
            //boolean isPass = score >= 30;

            // ✅ Show alert message with score and pass/fail
            //String resultMessage = "You have already completed the test. Your score: " + score + "%. " +
                    //(isPass ? "Congratulations, you passed!" : "Unfortunately, you failed.");

            //redirectAttributes.addFlashAttribute("testMessage", resultMessage);
            return "redirect:/instructions"; // ✅ Redirect with alert
        }

        // Fetch questions in random order
        List<Question> questions = questionRepository.findAll();
        Collections.shuffle(questions); // Randomize order of questions

        model.addAttribute("questions", questions);
        model.addAttribute("idNumber", userId);
        return "test";
    }

    @PostMapping("/submit")
    public String submitTest(HttpSession session, @RequestParam Map<String, String> answers) {
        String userId = (String) session.getAttribute("AUTHENTICATED_USER");

        if (userId == null) {
            return "redirect:/"; // Prevent unauthenticated users from submitting
        }

        if (answers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No answers submitted.");
        }

        // Save user responses
        for (Map.Entry<String, String> entry : answers.entrySet()) {
            try {
                Long questionId = Long.parseLong(entry.getKey().replace("question", ""));
                String answer = entry.getValue();

                Question question = questionRepository.findById(questionId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found: " + questionId));

                UserResponse response = new UserResponse();
                response.setUserId(userId);
                response.setQuestion(question);
                response.setAnswer(answer);

                userResponseRepository.save(response);
            } catch (NumberFormatException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid question ID format: " + entry.getKey());
            }
        }

        // ✅ Mark test as completed
        Optional<Applicant> applicant = idCheckService.findByIdNumber(userId);
        if (applicant.isPresent()) {
            Applicant existingApplicant = applicant.get();
            existingApplicant.setHasCompletedTest(true);
            idCheckService.saveApplicant(existingApplicant);
        }

        return "redirect:/result?id=" + userId;
    }

//    private int calculateUserScore(String userId) {
//        List<UserResponse> responses = userResponseRepository.findByUserId(userId);
//        if (responses.isEmpty()) return 0;
//
//        long correctAnswers = responses.stream()
//                .filter(response -> response.getAnswer().equalsIgnoreCase(response.getQuestion().getCorrectAnswer()))
//                .count();
//
//        return (int) ((correctAnswers * 100.0) / responses.size()); // Convert to percentage
//    }
}