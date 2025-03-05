package com.leanpay.loancalculator.repository;

import com.leanpay.loancalculator.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Optional<Loan> findByPrincipalAndInterestAndNumberOfMonths(BigDecimal principal, BigDecimal interest, int numberOfMonths);

}
