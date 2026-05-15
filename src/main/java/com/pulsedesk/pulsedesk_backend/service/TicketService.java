package com.pulsedesk.pulsedesk_backend.service;

import com.pulsedesk.pulsedesk_backend.entity.Comment;
import com.pulsedesk.pulsedesk_backend.entity.Ticket;
import com.pulsedesk.pulsedesk_backend.repository.CommentRepository;
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

    public Ticket createTicket(String content, Comment comment) {

        Ticket ticket = new Ticket();

        ticket.setTitle("User reported technical issue");
        ticket.setCategory("BUG");
        ticket.setPriority("HIGH");
        ticket.setSummary(content);
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
