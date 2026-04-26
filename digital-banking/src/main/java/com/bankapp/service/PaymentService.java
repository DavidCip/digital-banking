package com.bankapp.service;

import com.bankapp.dto.request.PaymentRequest;
import com.bankapp.dto.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse payBill(PaymentRequest request);

    PaymentResponse payFine(PaymentRequest request);
}