package com.example.LoadCalculator.api.dto;

public class LoanRequestDTO {

    private double loadAmount;
    private double annualInterest;
    private int numberOfMonths;

    public double getLoadAmount() {
        return loadAmount;
    }

    public double getAnnualInterest() {
        return annualInterest;
    }

    public int getNumberOfMonths() {
        return numberOfMonths;
    }
}
