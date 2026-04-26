package com.bankapp.controller;

import com.bankapp.config.ApiPaths;
import com.bankapp.dto.request.LostCardRequest;
import com.bankapp.dto.request.SupportTicketRequest;
import com.bankapp.dto.response.SupportTicketResponse;
import com.bankapp.service.SupportService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.SUPPORT)
public class SupportController {

    private final SupportService supportService;

    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }

    @PostMapping(ApiPaths.TICKET)
    public ResponseEntity<SupportTicketResponse> createTicket(@Valid @RequestBody SupportTicketRequest request) {
        return ResponseEntity.ok(supportService.createTicket(request));
    }

    @PostMapping(ApiPaths.LOST_CARD)
    public ResponseEntity<SupportTicketResponse> reportLostCard(@Valid @RequestBody LostCardRequest request) {
        return ResponseEntity.ok(supportService.reportLostCard(request));
    }

    @PostMapping(ApiPaths.FORGOT_PIN)
    public ResponseEntity<SupportTicketResponse> forgotPin(@Valid @RequestBody SupportTicketRequest request) {
        return ResponseEntity.ok(supportService.forgotPin(request));
    }

    @PostMapping(ApiPaths.CALL_ASSISTANCE)
    public ResponseEntity<SupportTicketResponse> callAssistance(@Valid @RequestBody SupportTicketRequest request) {
        return ResponseEntity.ok(supportService.callAssistance(request));
    }

    @GetMapping(ApiPaths.USER_BY_ID)
    public ResponseEntity<List<SupportTicketResponse>> getTicketsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(supportService.getTicketsByUserId(userId));
    }
}