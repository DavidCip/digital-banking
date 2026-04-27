package com.bankapp.service;

import com.bankapp.dto.request.TransferRequest;
import com.bankapp.dto.response.TransferResponse;

public interface TransferService {

    TransferResponse transferToUser(TransferRequest request);
}