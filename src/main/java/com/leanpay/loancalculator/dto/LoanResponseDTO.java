package com.leanpay.loancalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponseDTO {

    private BigDecimal loan;
    private BigDecimal interestRate;
    private int monthlyPayments;
    private BigDecimal totalPayments;
    private BigDecimal totalInterest;
    @Builder.Default
    private List<PaymentScheduleResponseDTO> paymentSchedules = new ArrayList<>();

}



