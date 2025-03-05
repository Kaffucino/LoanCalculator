package com.leanpay.loancalculator.loanCalculatorHelperTests;

import com.leanpay.loancalculator.helper.LoanCalculatorHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = LoanCalculatorHelper.class)
public class LoanCalculatorHelperTest {

    @Autowired
    private LoanCalculatorHelper loanCalculatorHelper;

    @ParameterizedTest
    @CsvSource({
            "5, 0.00416667",
            "4, 0.00333333",
            "0, 0E-8",
            "1.8, 0.00150000"
    })
    public void testCalculateMonthlyInterest(BigDecimal interestRate, BigDecimal expectedMonthlyInterestRate)
    {
        BigDecimal actualMonthlyInterestRate = loanCalculatorHelper.calculateMonthlyInterestRate(interestRate);

        assertEquals(expectedMonthlyInterestRate, actualMonthlyInterestRate, "monthly interest rate should match the expected value.");
    }


    @ParameterizedTest
    @CsvSource({
            "1000, 0.00416667, 4.17",
            "200, 0.00416667, 0.83",
    })
    public void testCalculateInterestAmount(BigDecimal balance, BigDecimal monthlyInterestRate, BigDecimal expectedInterestRate)
    {
        BigDecimal actualInterestAmount = loanCalculatorHelper.calculateInterestAmount(balance, monthlyInterestRate);

        assertEquals(expectedInterestRate, actualInterestAmount, "expected interest rate should match the expected value.");
    }


    @ParameterizedTest
    @CsvSource({
            "1000, 5, 10, 102.31",
            "30000, 4.8, 30, 1063.20",
            "0, 2, 5, 0.00",
            "3000, 0, 12, 250.00",
            "10000, 2, 1, 10016.67"
    })
    public void testCalculateMonthlyPayment(BigDecimal loanAmount, BigDecimal interestRate, int numberOfMonths, BigDecimal expectedMonthlyPayment)
    {

        BigDecimal monthlyInterestRate = loanCalculatorHelper.calculateMonthlyInterestRate(interestRate);
        BigDecimal actualMonthlyPayment = loanCalculatorHelper.calculateMonthlyPayment(loanAmount, monthlyInterestRate, numberOfMonths);

        assertEquals(expectedMonthlyPayment, actualMonthlyPayment, "The monthly payment should match the expected value.");
    }


}
