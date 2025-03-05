package com.leanpay.loancalculator.service;

import com.leanpay.loancalculator.dto.LoanResponseDTO;

import java.math.BigDecimal;

public interface LoanCalculatorService {

    LoanResponseDTO calculateLoanPaymentPlan(BigDecimal loanAmount, BigDecimal annualInterest, int numberOfMonths);
}
