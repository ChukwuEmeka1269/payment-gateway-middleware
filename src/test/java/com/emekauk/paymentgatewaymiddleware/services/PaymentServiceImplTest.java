package com.emekauk.paymentgatewaymiddleware.services;

import com.emekauk.paymentgatewaymiddleware.domain.entity.Payment;
import com.emekauk.paymentgatewaymiddleware.domain.mappers.Mapper;

import com.emekauk.paymentgatewaymiddleware.domain.responses.PaymentResponse;
import com.emekauk.paymentgatewaymiddleware.downstreamService.BankServicePaymentResponse;
import com.emekauk.paymentgatewaymiddleware.repositories.PaymentRepository;
import com.emekauk.paymentgatewaymiddleware.util.TestDataUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import static com.emekauk.paymentgatewaymiddleware.downstreamService.BankServicePaymentResponse.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
@SpringBootTest
class PaymentServiceImplTest {


    @MockBean
    private PaymentRepository paymentRepository;

    @Mock
    private Mapper<Payment, PaymentResponse> paymentMapper;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentServiceImpl(paymentRepository, paymentMapper, restTemplate);
    }

    @Test
    void testInitiatePayment() {
        // Mocked behavior of restTemplate.postForObject()
        BankServicePaymentResponse mockResponse = new BankServicePaymentResponse();
        mockResponse.setStatus(Status.PS);
        when(restTemplate.postForObject(any(), any(), any())).thenReturn(mockResponse);

        // Mocked behavior of paymentRepository.save()
        Payment savedPayment = new Payment();
        when(paymentRepository.save(any())).thenReturn(savedPayment);

        // Mocked behavior of paymentMapper.mapTo()
        PaymentResponse mockedPaymentResponse = TestDataUtil.successfulPaymentResponse();
        when(paymentMapper.mapTo(any())).thenReturn(mockedPaymentResponse);

        // assertion test
        PaymentResponse response = paymentService.initiatePayment(TestDataUtil.paymentRequest());
        assertEquals(mockedPaymentResponse, response);
    }

}