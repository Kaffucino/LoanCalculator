package com.example.LoadCalculator.api.controller;

import com.example.LoadCalculator.api.dto.LoanRequestDTO;
import com.example.LoadCalculator.api.dto.LoanResponseDTO;
import com.example.LoadCalculator.service.LoanCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/loanCalculator")
public class LoadCalculatorController {

    @Autowired
    private LoanCalculatorService loanCalculatorService;



    @PostMapping("/calculate")
    public ResponseEntity<List<LoanResponseDTO>> performLoanCalculation(@RequestBody LoanRequestDTO loanRequest)
    {
        List<LoanResponseDTO> loanPlan = loanCalculatorService.calculateLoanPaymentPlan(
                loanRequest.getLoadAmount(),
                loanRequest.getAnnualInterest(),
                loanRequest.getNumberOfMonths()
        );


        return ResponseEntity.ok(loanPlan);
    }


}
