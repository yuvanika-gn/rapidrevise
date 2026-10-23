package com.simats.rapidrevise.response;

import com.simats.rapidrevise.model.Flashcard;

import java.util.List;

public class FlashcardResponse {
    private String status;
    private List<Flashcard> flashcards;

    public String getStatus() {
        return status;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }
}
