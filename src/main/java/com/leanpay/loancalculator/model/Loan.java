package com.leanpay.loancalculator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "loan")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_months", nullable = false)
    private int numberOfMonths;
    @Column(name = "total_payment", nullable = false)
    private BigDecimal totalPayment;
    @Column(name = "principal", nullable = false)
    private BigDecimal principal;
    @Column(name = "interest", nullable = false)
    private BigDecimal interest;
    @Column(name = "total_interest", nullable = false)
    private BigDecimal totalInterest;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    @Builder.Default
    private List<PaymentSchedule> paymentSchedules = new ArrayList<>();

}
