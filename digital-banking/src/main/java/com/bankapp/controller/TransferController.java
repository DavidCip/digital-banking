package com.bankapp.controller;

import com.bankapp.config.ApiPaths;
import com.bankapp.dto.request.UserTransferRequest;
import com.bankapp.dto.response.TransferResponse;
import com.bankapp.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.TRANSFERS)
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping(ApiPaths.SEND)
    public ResponseEntity<TransferResponse> transferToUser(@Valid @RequestBody UserTransferRequest request) {
        return ResponseEntity.ok(transferService.transferToUser(request));
    }
}