package com.bankapp.service;

import com.bankapp.dto.request.LostCardRequest;
import com.bankapp.dto.request.SupportTicketRequest;
import com.bankapp.dto.response.SupportTicketResponse;

import java.util.List;

public interface SupportService {

    SupportTicketResponse createTicket(SupportTicketRequest request);

    SupportTicketResponse reportLostCard(LostCardRequest request);

    SupportTicketResponse forgotPin(SupportTicketRequest request);

    SupportTicketResponse callAssistance(SupportTicketRequest request);

    List<SupportTicketResponse> getTicketsByUserId(Long userId);
}