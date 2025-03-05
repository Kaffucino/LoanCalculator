package com.leanpay.loancalculator.controller;

import com.leanpay.loancalculator.dto.LoanRequestDTO;
import com.leanpay.loancalculator.dto.LoanResponseDTO;
import com.leanpay.loancalculator.service.LoanCalculatorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/loanCalculator")
@Validated
public class LoanCalculatorController {

    @Autowired
    private LoanCalculatorService loanCalculatorService;

    @PostMapping("/calculate")
    public ResponseEntity<LoanResponseDTO> performLoanCalculation(@Valid @RequestBody LoanRequestDTO loanRequest)
    {
        LoanResponseDTO loanPlan = loanCalculatorService.calculateLoanPaymentPlan(
                loanRequest.getLoanAmount(),
                loanRequest.getAnnualInterest(),
                loanRequest.getNumberOfMonths()
        );


        return ResponseEntity.ok(loanPlan);
    }


}
