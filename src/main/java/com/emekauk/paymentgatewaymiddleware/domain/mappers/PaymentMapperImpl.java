package com.emekauk.paymentgatewaymiddleware.domain.mappers;

import com.emekauk.paymentgatewaymiddleware.domain.entity.Payment;
import com.emekauk.paymentgatewaymiddleware.domain.responses.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMapperImpl implements Mapper<Payment, PaymentResponse> {

    private final ModelMapper modelMapper;

    @Override
    public PaymentResponse mapTo(Payment payment) {

        if(payment != null){
            return modelMapper.map(payment, PaymentResponse.class);
        }
        return null;
    }

    @Override
    public Payment mapFrom(PaymentResponse paymentResponse) {
        if(paymentResponse != null){
            return modelMapper.map(paymentResponse, Payment.class);
        }
        return null;
    }
}
