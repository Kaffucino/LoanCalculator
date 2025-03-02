package com.example.LoadCalculator.ControllerTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoanCalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCalculateLoan() throws Exception {
        String jsonRequest = "{\"loadAmount\":1000,\"annualInterest\":5,\"numberOfMonths\":10}";

        mockMvc.perform(post("/api/loanCalculator/calculate")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }
}
