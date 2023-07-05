package com.example.be.service.vnpay;

import com.example.be.DTO.payment.PaymentSendEmailDTO;

public interface IVNPayService {
    void sendEmail(PaymentSendEmailDTO paymentSendEmailDTO);
}