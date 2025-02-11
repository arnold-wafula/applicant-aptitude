package com.postbank.applicantaptitude.service;

import com.postbank.applicantaptitude.entity.Question;
import com.postbank.applicantaptitude.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        if (questions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No questions found.");
        }

        Collections.shuffle(questions); // Shuffle
        return questions;
    }

    public List<Question> getQuestionsByCategory(String category) {
        List<Question> questions = questionRepository.findByCategory(category);
        if (questions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No questions found for category: " + category);
        }

        Collections.shuffle(questions); // Shuffle
        return questions;
    }
}