package com.example.LoadCalculator.api.repository;

import com.example.LoadCalculator.api.model.LoanPaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILoanAmortizationRepository extends JpaRepository<LoanPaymentModel, Long> {
}
