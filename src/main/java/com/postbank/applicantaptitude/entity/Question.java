package com.postbank.applicantaptitude.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "tm_recruitment_aptitude_questions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question; // TEXT ensures longer questions are handled well

    @Column(name = "option_a", nullable = false)
    private String optionA;

    @Column(name = "option_b", nullable = false)
    private String optionB;

    @Column(name = "option_c", nullable = false)
    private String optionC;

    @Column(name = "option_d", nullable = false)
    private String optionD;

    @Column(name = "correct_option", nullable = false)
    private Character correctOption; // Changed `char` to `Character` to avoid primitive issues

    // Default constructor (required by JPA)
    public Question() {}

    // Parameterized constructor for convenience
    public Question(String category, String question, String optionA, String optionB, String optionC, String optionD, Character correctOption) {
        this.category = category;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getQuestion() { return question; }

    public void setQuestion(String question) { this.question = question; }

    public String getOptionA() { return optionA; }

    public void setOptionA(String optionA) { this.optionA = optionA; }

    public String getOptionB() { return optionB; }

    public void setOptionB(String optionB) { this.optionB = optionB; }

    public String getOptionC() { return optionC; }

    public void setOptionC(String optionC) { this.optionC = optionC; }

    public String getOptionD() { return optionD; }

    public void setOptionD(String optionD) { this.optionD = optionD; }

    public Character getCorrectOption() { return correctOption; }

    public void setCorrectOption(Character correctOption) { this.correctOption = correctOption; }
}