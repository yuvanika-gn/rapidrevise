package com.simats.rapidrevise.model;

public class ProgressModel {

    private String subject_name;
    private int concepts;
    private int flashcards;
    private int formulas;
    private int total_activity;
    private double accuracy;

    public String getSubject_name() { return subject_name; }
    public int getConcepts() { return concepts; }
    public int getFlashcards() { return flashcards; }
    public int getFormulas() { return formulas; }
    public int getTotal_activity() { return total_activity; }
    public double getAccuracy() { return accuracy; }
}
