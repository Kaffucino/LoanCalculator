package com.leanpay.loancalculator.service;

import com.leanpay.loancalculator.dto.LoanResponseDTO;
import com.leanpay.loancalculator.dto.PaymentScheduleResponseDTO;
import com.leanpay.loancalculator.helper.LoanCalculatorHelper;
import com.leanpay.loancalculator.mapper.PaymentScheduleMapper;
import com.leanpay.loancalculator.model.Loan;
import com.leanpay.loancalculator.model.PaymentSchedule;
import com.leanpay.loancalculator.repository.LoanRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class LoanCalculatorServiceImpl implements LoanCalculatorService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private PaymentScheduleMapper paymentScheduleMapper;

    @Autowired
    private LoanCalculatorHelper loanCalculatorHelper;

    private static final Logger logger = LoggerFactory.getLogger(LoanCalculatorServiceImpl.class);

    @Transactional
    public LoanResponseDTO calculateLoanPaymentPlan(BigDecimal loanAmount, BigDecimal annualInterest, int numberOfMonths)
    {
        logger.info("Execution of {} has started.", LoanCalculatorServiceImpl.class.getName());

        LoanResponseDTO responseDTO = new LoanResponseDTO();

        Optional<Loan> optionalLoan = loanRepository.findByPrincipalAndInterestAndNumberOfMonths(loanAmount, annualInterest, numberOfMonths);

        if(optionalLoan.isPresent())
        {
            logger.info("Loan entity for values loanAmount = {}, interestRate = {}, numberOfMonths = {} is present in database. Loan entity will be fethced.", loanAmount, annualInterest, numberOfMonths);

            Loan loan = optionalLoan.get();
           // loan.getPaymentSchedules().forEach(paymentSchedule -> paymentPlan.add(DTOMapper.mapToInstallmentDTO(paymentSchedule)));
            List<PaymentScheduleResponseDTO> list = loan.getPaymentSchedules().stream().map(paymentScheduleMapper::fromDomainToDTO).toList();

            responseDTO.setPaymentSchedules(list);
            responseDTO.setTotalPayments(loan.getTotalPayment());
            responseDTO.setTotalInterest(loan.getTotalInterest());
        }
        else
        {
            logger.info("Loan entity for values loanAmount = {}, interestRate = {}, numberOfMonths = {} is not present in database. Loan entity will be created.", loanAmount, annualInterest, numberOfMonths);

            Loan loan = Loan.builder()
                     .principal(loanAmount)
                     .numberOfMonths(numberOfMonths)
                     .interest(annualInterest)
                     .build();

            BigDecimal monthlyInterestRate = loanCalculatorHelper.calculateMonthlyInterestRate(annualInterest);
            BigDecimal monthlyPayment = loanCalculatorHelper.calculateMonthlyPayment(loanAmount, monthlyInterestRate, numberOfMonths);
            BigDecimal totalBalance = loanAmount;

            BigDecimal totalPayment = BigDecimal.ZERO;
            BigDecimal totalInterest = BigDecimal.ZERO;

            for(int i = 0; i < numberOfMonths; ++i)
            {

                BigDecimal interestAmount = loanCalculatorHelper.calculateInterestAmount(totalBalance, monthlyInterestRate);
                BigDecimal principalAmount = loanCalculatorHelper.calculatePrincipalAmount(monthlyPayment, interestAmount);

                //calculation for the last month
                if(i == numberOfMonths - 1)
                {
                    principalAmount = totalBalance;
                    totalBalance = BigDecimal.ZERO;
                    monthlyPayment = principalAmount.add(interestAmount);

                }else
                {
                    totalBalance = totalBalance.subtract(principalAmount);
                }

                totalPayment = totalPayment.add(monthlyPayment);
                totalInterest = totalInterest.add(interestAmount);

                PaymentSchedule paymentSchedule = PaymentSchedule.builder()
                        .paymentMonth(i+1)
                        .paymentAmount(monthlyPayment)
                        .principalAmount(principalAmount)
                        .interestAmount(interestAmount)
                        .balanceOwed(totalBalance)
                        .loan(loan)
                        .build();

                loan.getPaymentSchedules().add(paymentSchedule);

                responseDTO.getPaymentSchedules().add(paymentScheduleMapper.fromDomainToDTO(paymentSchedule));
            }

            responseDTO.setTotalPayments(totalPayment);
            responseDTO.setTotalInterest(totalInterest);

            loan.setTotalPayment(totalPayment);
            loan.setTotalInterest(totalInterest);
            loanRepository.save(loan);
        }

        responseDTO.setLoan(loanAmount);
        responseDTO.setInterestRate(annualInterest);
        responseDTO.setMonthlyPayments(numberOfMonths);

        return responseDTO;
    }


}
