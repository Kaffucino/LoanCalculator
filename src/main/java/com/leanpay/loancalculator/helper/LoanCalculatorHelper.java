package com.leanpay.loancalculator.helper;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class LoanCalculatorHelper {

    public BigDecimal calculateMonthlyPayment(BigDecimal loanAmount, BigDecimal monthlyInterestRate, int numberOfMonths)
    {

        if (monthlyInterestRate.compareTo(BigDecimal.ZERO) == 0) {
            return loanAmount.divide(BigDecimal.valueOf(numberOfMonths), 2, RoundingMode.HALF_UP);
        }

        //(1 + i)
        BigDecimal onePlusRate = BigDecimal.ONE.add(monthlyInterestRate);

        //(1 + i) ^ n
        BigDecimal numeratorPow = onePlusRate.pow(numberOfMonths);

        //P * i * (1 + i)^n
        BigDecimal numerator = loanAmount.multiply(monthlyInterestRate).multiply(numeratorPow);

        //(1 + i)^n - 1
        BigDecimal denominator = numeratorPow.subtract(BigDecimal.ONE);

        // numerator / denominator
        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateMonthlyInterestRate(BigDecimal annualInterest) {

        return annualInterest.divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP).divide(BigDecimal.valueOf(12), 8, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateInterestAmount(BigDecimal balance, BigDecimal monthlyInterestRate) {

        return balance.multiply(monthlyInterestRate).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculatePrincipalAmount(BigDecimal monthlyPayment, BigDecimal interestAmount) {

        return monthlyPayment.subtract(interestAmount).setScale(2, RoundingMode.HALF_UP);
    }
}
