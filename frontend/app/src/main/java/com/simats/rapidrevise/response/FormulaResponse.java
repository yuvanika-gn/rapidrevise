package com.simats.rapidrevise.response;

import com.simats.rapidrevise.model.Formula;

import java.util.List;

public class FormulaResponse {

    private String status;
    private List<Formula> formulas;

    public String getStatus() {
        return status;
    }

    public List<Formula> getFormulas() {
        return formulas;
    }
}