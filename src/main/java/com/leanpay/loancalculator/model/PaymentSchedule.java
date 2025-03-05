package com.leanpay.loancalculator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "payment_schedule")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "payment_month")
    private int paymentMonth;
    @Column(name = "payment_amount")
    private BigDecimal paymentAmount;
    @Column(name = "principal_amount")
    private BigDecimal principalAmount;
    @Column(name = "interest_amount")
    private BigDecimal interestAmount;
    @Column(name = "balance_owed")
    private BigDecimal balanceOwed;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;


}
