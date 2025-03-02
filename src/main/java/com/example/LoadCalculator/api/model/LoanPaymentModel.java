package com.example.LoadCalculator.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class LoanPaymentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numberOfMonths;
    private double payment;
    private double principal;
    private double interest;

    public LoanPaymentModel() {}

    public LoanPaymentModel(Long id, int numberOfMonths, double payment, double interest, double principal) {
        this.id = id;
        this.numberOfMonths = numberOfMonths;
        this.payment = payment;
        this.interest = interest;
        this.principal = principal;
    }

    public Long getId() {
        return id;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public int getNumberOfMonths() {
        return numberOfMonths;
    }

    public void setNumberOfMonths(int numberOfMonths) {
        this.numberOfMonths = numberOfMonths;
    }
}
