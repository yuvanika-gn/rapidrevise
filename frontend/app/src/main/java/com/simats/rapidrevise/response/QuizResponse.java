package com.simats.rapidrevise.response;

import com.simats.rapidrevise.model.QuizQuestion;

import java.util.List;

public class QuizResponse {

    private String status;
    private List<QuizQuestion> quiz;

    public String getStatus() {
        return status;
    }

    public List<QuizQuestion> getQuiz() {
        return quiz;
    }
}
