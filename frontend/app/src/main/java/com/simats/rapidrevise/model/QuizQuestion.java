package com.simats.rapidrevise.model;

import java.util.List;

public class QuizQuestion {

    private String question;
    private List<String> options;
    private String correctAnswer;
    private String selectedAnswer;

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}