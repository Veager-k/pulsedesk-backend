package com.pulsedesk.pulsedesk_backend.service;

import com.pulsedesk.pulsedesk_backend.dto.TicketAnalysisResult;
import com.pulsedesk.pulsedesk_backend.entity.Comment;
import com.pulsedesk.pulsedesk_backend.entity.Ticket;
import com.pulsedesk.pulsedesk_backend.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(
            TicketAnalysisResult analysis,
            Comment comment
    ) {

        Ticket ticket = new Ticket();

        ticket.setTitle(analysis.getTitle());
        ticket.setCategory(analysis.getCategory());
        ticket.setPriority(analysis.getPriority());
        ticket.setSummary(analysis.getSummary());

        ticket.setCreatedAt(LocalDateTime.now());

        ticket.setComment(comment);

        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }
}