package com.leanpay.loancalculator.helper;

import com.leanpay.loancalculator.dto.LoanResponseDTO;
import com.leanpay.loancalculator.dto.PaymentScheduleResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class MockDataHelper {

    public LoanResponseDTO createMockLoanResponse(BigDecimal loanAmount, BigDecimal interestRate, int numberOfMonths,
                                                  BigDecimal totalPayment, BigDecimal totalInterest, List<PaymentScheduleResponseDTO> paymentSchedules) {

        return LoanResponseDTO.builder()
                .loan(loanAmount)
                .interestRate(interestRate)
                .monthlyPayments(numberOfMonths)
                .totalPayments(totalPayment)
                .totalInterest(totalInterest)
                .paymentSchedules(paymentSchedules)
                .build();
    }

    public PaymentScheduleResponseDTO createMockScheduleResponse(int month, BigDecimal paymentAmount, BigDecimal principalAmount, BigDecimal interestAmount, BigDecimal balanceOwed)
    {

        return PaymentScheduleResponseDTO.builder()
                .month(month)
                .paymentAmount(paymentAmount)
                .principalAmount(principalAmount)
                .interestAmount(interestAmount)
                .balanceOwed(balanceOwed)
                .build();

    }
}