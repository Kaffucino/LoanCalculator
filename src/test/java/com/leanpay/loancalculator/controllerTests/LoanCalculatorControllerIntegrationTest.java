package com.leanpay.loancalculator.controllerTests;

import com.jayway.jsonpath.JsonPath;
import com.leanpay.loancalculator.model.Loan;
import com.leanpay.loancalculator.repository.LoanRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoanCalculatorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoanRepository loanRepository;

    static Stream<Arguments> loanRequests()
    {
        return Stream.of(

                Arguments.of("{\"loanAmount\":1000,\"annualInterest\":5,\"numberOfMonths\":10}", HttpStatus.valueOf(200)),
                Arguments.of("{\"loanAmount\":0,\"annualInterest\":5,\"numberOfMonths\":10}", HttpStatus.valueOf(200)),
                Arguments.of("{\"loanAmount\":1000,\"annualInterest\":0,\"numberOfMonths\":10}", HttpStatus.valueOf(200)),
                Arguments.of("{\"loanAmount\":1000,\"annualInterest\":5,\"numberOfMonths\":1}", HttpStatus.valueOf(200)),
                Arguments.of("{\"loanAmount\":0,\"annualInterest\":0,\"numberOfMonths\":1}", HttpStatus.valueOf(200)),
                Arguments.of("{\"loanAmount\":null,\"annualInterest\":5,\"numberOfMonths\":10}", HttpStatus.valueOf(400)),
                Arguments.of( "{\"loanAmount\":-1000,\"annualInterest\":5,\"numberOfMonths\":10}", HttpStatus.valueOf(400)),
                Arguments.of("{\"loanAmount\":1000,\"annualInterest\":null,\"numberOfMonths\":10}", HttpStatus.valueOf(400)),
                Arguments.of("{\"loanAmount\":1000,\"annualInterest\":-5,\"numberOfMonths\":10}", HttpStatus.valueOf(400)),
                Arguments.of("{\"loanAmount\":1000,\"annualInterest\":5,\"numberOfMonths\":null}", HttpStatus.valueOf(400)),
                Arguments.of("{\"loanAmount\":1000,\"annualInterest\":5,\"numberOfMonths\":-10}", HttpStatus.valueOf(400)),
                Arguments.of("{\"loanAmount\":1000,\"annualInterest\":5,\"numberOfMonths\":0}", HttpStatus.valueOf(400))
        );
    }

    @ParameterizedTest
    @MethodSource("loanRequests")
    public void testCalculateLoanRequestForVariousInputs(String jsonRequest, HttpStatus expectedStatus) throws Exception
    {
        mockMvc.perform(post("/api/v1/loanCalculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().is(expectedStatus.value()));
    }

    @Test
    @Transactional
    public void testLoanCalculatorHappyFlow() throws Exception
    {
        BigDecimal loanAmount = BigDecimal.valueOf(1000);
        BigDecimal annualInterest = BigDecimal.valueOf(5);
        int numberOfMonths = 3;

        String jsonRequest = String.format("{\"loanAmount\":%s,\"annualInterest\":%s,\"numberOfMonths\":%d}", loanAmount, annualInterest, numberOfMonths) ;

        MvcResult result = mockMvc.perform(post("/api/v1/loanCalculator/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();

        BigDecimal loanAmountFromResponse = JsonPath.parse(responseContent).read("$.loan", BigDecimal.class);
        BigDecimal interestRateFromResponse = JsonPath.parse(responseContent).read("$.interestRate", BigDecimal.class);
        int numberOfMonthsFromResponse = JsonPath.parse(responseContent).read("$.monthlyPayments", Integer.class);

        BigDecimal totalPayments = JsonPath.parse(responseContent).read("$.totalPayments", BigDecimal.class);
        BigDecimal totalInterest = JsonPath.parse(responseContent).read("$.totalInterest", BigDecimal.class);

        List paymentSchedules = JsonPath.parse(responseContent).read("$.paymentSchedules", List.class);


        assertThat(loanAmount).isEqualTo(loanAmountFromResponse);
        assertThat(annualInterest).isEqualTo(interestRateFromResponse);
        assertThat(numberOfMonths).isEqualTo(numberOfMonthsFromResponse);
        assertThat(paymentSchedules.size()).isEqualTo(3);


        Optional<Loan> optionalLoan = loanRepository.findByPrincipalAndInterestAndNumberOfMonths(loanAmount, annualInterest, numberOfMonths);
        assertThat(optionalLoan).isPresent();

        Loan loan = optionalLoan.get();

        assertThat(loan.getTotalPayment()).isEqualTo(totalPayments);
        assertThat(loan.getTotalInterest()).isEqualTo(totalInterest);
        assertThat(loan.getPaymentSchedules().size()).isEqualTo(3);
    }

}
