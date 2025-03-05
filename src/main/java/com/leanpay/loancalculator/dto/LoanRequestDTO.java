package com.leanpay.loancalculator.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanRequestDTO {

    @NotNull(message = "Loan amount cannot be null")
    @PositiveOrZero(message = "Loan amount must be a positive value")
    private BigDecimal loanAmount;

    @NotNull(message = "annual interest cannot be null")
    @PositiveOrZero(message = "annual interest must be a positive value")
    private BigDecimal annualInterest;

    @NotNull(message = "number of months cannot be null")
    @Min(value = 1, message = "number of months must be greater than 0")
    private int numberOfMonths;

}
