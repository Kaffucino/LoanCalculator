package com.leanpay.loancalculator.mapper;

import com.leanpay.loancalculator.dto.PaymentScheduleResponseDTO;
import com.leanpay.loancalculator.model.PaymentSchedule;
import org.springframework.stereotype.Component;

@Component
public class PaymentScheduleMapper {

    public PaymentScheduleResponseDTO fromDomainToDTO(PaymentSchedule schedule) {

        return PaymentScheduleResponseDTO.builder()
                .month(schedule.getPaymentMonth())
                .paymentAmount(schedule.getPaymentAmount())
                .principalAmount(schedule.getPrincipalAmount())
                .interestAmount(schedule.getInterestAmount())
                .balanceOwed(schedule.getBalanceOwed())
                .build();
    }
}
