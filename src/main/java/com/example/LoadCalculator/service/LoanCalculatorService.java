package com.example.LoadCalculator.service;

import com.example.LoadCalculator.api.dto.LoanResponseDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanCalculatorService {

    public List<LoanResponseDTO> calculateLoanPaymentPlan(double loanAmount, double annualInterest, int numberOfMonths)
    {
        List<LoanResponseDTO> paymentPlan = new ArrayList<>();

        double monthlyInterestRate = (annualInterest / 100) / 12;
        BigDecimal monthlyPayment = BigDecimal.valueOf(calculateMonthlyPayment(loanAmount, monthlyInterestRate, numberOfMonths)).setScale(2, RoundingMode.HALF_UP) ;

        BigDecimal totalBalance = BigDecimal.valueOf(loanAmount);

        for(int i = 0; i < numberOfMonths; ++i)
        {
            BigDecimal interestAmount = totalBalance.multiply(BigDecimal.valueOf(monthlyInterestRate)).setScale(2, RoundingMode.HALF_UP);
            BigDecimal principalAmount =  monthlyPayment.subtract(interestAmount);

            //calculation for the last month
            if(i == numberOfMonths - 1)
            {
                principalAmount = totalBalance;
                totalBalance = BigDecimal.valueOf(0);
                monthlyPayment = principalAmount.add(interestAmount);

            }else
            {
                totalBalance = totalBalance.subtract(principalAmount);
            }

            LoanResponseDTO element = new LoanResponseDTO(i+1, monthlyPayment, principalAmount, interestAmount, totalBalance);

            paymentPlan.add(element);
        }

        return paymentPlan;
    }

    private double calculateMonthlyPayment(double loanAmount, double monthlyInterestRate, int numberOfMonths)
    {
        return (loanAmount * monthlyInterestRate * Math.pow((1 + monthlyInterestRate), numberOfMonths)) /
                (Math.pow((1 + monthlyInterestRate), numberOfMonths) - 1);
    }

}
