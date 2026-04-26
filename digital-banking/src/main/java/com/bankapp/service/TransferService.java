package com.bankapp.service;

import com.bankapp.dto.request.UserTransferRequest;
import com.bankapp.dto.response.TransferResponse;

public interface TransferService {

    TransferResponse transferToUser(UserTransferRequest request);
}