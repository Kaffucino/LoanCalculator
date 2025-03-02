package com.example.LoadCalculator.api.dto;

import java.math.BigDecimal;

public class LoanResponseDTO {

    private int month;
    private BigDecimal paymentAmount;
    private BigDecimal principalAmount;
    private BigDecimal interestAmount;
    private BigDecimal balanceOwed;

    public LoanResponseDTO(int month, BigDecimal paymentAmount, BigDecimal principalAmount, BigDecimal interestAmount, BigDecimal balanceOwed) {
        this.month = month;
        this.paymentAmount = paymentAmount;
        this.principalAmount = principalAmount;
        this.interestAmount = interestAmount;
        this.balanceOwed = balanceOwed;
    }

    public int getMonth() {
        return month;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public BigDecimal getPrincipalAmount() {
        return principalAmount;
    }

    public BigDecimal getInterestAmount() {
        return interestAmount;
    }

    public BigDecimal getBalanceOwed() {
        return balanceOwed;
    }
}
