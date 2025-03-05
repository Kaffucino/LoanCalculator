package com.leanpay.loancalculator.controllerTests;

import com.leanpay.loancalculator.dto.LoanResponseDTO;
import com.leanpay.loancalculator.dto.PaymentScheduleResponseDTO;
import com.leanpay.loancalculator.helper.MockDataHelper;
import com.leanpay.loancalculator.repository.LoanRepository;
import com.leanpay.loancalculator.service.LoanCalculatorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoanCalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoanCalculatorService loanCalculatorService;

    @MockitoBean
    private LoanRepository loanRepository;

    @Autowired
    private MockDataHelper mockDataHelper;

    @Test
    public void loanCalculationHappyFlowTest() throws Exception
    {

        BigDecimal loanAmount = BigDecimal.valueOf(1000);
        BigDecimal interestRate = BigDecimal.valueOf(5);
        int numberOfMonths = 3;

        PaymentScheduleResponseDTO paymentSchedule1 = mockDataHelper.createMockScheduleResponse(1, BigDecimal.valueOf(336.11), BigDecimal.valueOf(331.94)
                , BigDecimal.valueOf(4.17), BigDecimal.valueOf(668.06));

        PaymentScheduleResponseDTO paymentSchedule2 = mockDataHelper.createMockScheduleResponse(2, BigDecimal.valueOf(336.11), BigDecimal.valueOf(333.33)
                , BigDecimal.valueOf(2.78), BigDecimal.valueOf(334.73));

        PaymentScheduleResponseDTO paymentSchedule3 = mockDataHelper.createMockScheduleResponse(3, BigDecimal.valueOf(336.12), BigDecimal.valueOf(334.73)
                , BigDecimal.valueOf(1.39), BigDecimal.valueOf(0.00));

        List<PaymentScheduleResponseDTO> paymentSchedules = List.of(paymentSchedule1, paymentSchedule2, paymentSchedule3);

        LoanResponseDTO mockResponse = mockDataHelper.createMockLoanResponse(loanAmount, interestRate, numberOfMonths, BigDecimal.valueOf(1008.34)
                , BigDecimal.valueOf(8.34), paymentSchedules);


        Mockito.when(loanCalculatorService.calculateLoanPaymentPlan(
                        loanAmount,
                        interestRate,
                        numberOfMonths))
                .thenReturn(mockResponse);

        String jsonRequest = "{\"loanAmount\":1000,\"annualInterest\":5,\"numberOfMonths\":3}";

        mockMvc.perform(post("/api/v1/loanCalculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loan").value(loanAmount))
                .andExpect(jsonPath("$.interestRate").value(interestRate))
                .andExpect(jsonPath("$.monthlyPayments").value(numberOfMonths))
                .andExpect(jsonPath("$.totalPayments").value(1008.34))
                .andExpect(jsonPath("$.totalInterest").value(8.34))
                .andExpect(jsonPath("$.paymentSchedules[0].month").value(1))
                .andExpect(jsonPath("$.paymentSchedules[0].paymentAmount").value(336.11))
                .andExpect(jsonPath("$.paymentSchedules[0].principalAmount").value(331.94))
                .andExpect(jsonPath("$.paymentSchedules[0].interestAmount").value(4.17))
                .andExpect(jsonPath("$.paymentSchedules[0].balanceOwed").value(668.06))
                .andExpect(jsonPath("$.paymentSchedules[1].month").value(2))
                .andExpect(jsonPath("$.paymentSchedules[1].paymentAmount").value(336.11))
                .andExpect(jsonPath("$.paymentSchedules[1].principalAmount").value(333.33))
                .andExpect(jsonPath("$.paymentSchedules[1].interestAmount").value(2.78))
                .andExpect(jsonPath("$.paymentSchedules[1].balanceOwed").value(334.73))
                .andExpect(jsonPath("$.paymentSchedules[2].month").value(3))
                .andExpect(jsonPath("$.paymentSchedules[2].paymentAmount").value(336.12))
                .andExpect(jsonPath("$.paymentSchedules[2].principalAmount").value(334.73))
                .andExpect(jsonPath("$.paymentSchedules[2].interestAmount").value(1.39))
                .andExpect(jsonPath("$.paymentSchedules[2].balanceOwed").value(0.00));

        Mockito.verify(loanCalculatorService, times(1))
                .calculateLoanPaymentPlan(BigDecimal.valueOf(1000), BigDecimal.valueOf(5), 3);

    }

}

