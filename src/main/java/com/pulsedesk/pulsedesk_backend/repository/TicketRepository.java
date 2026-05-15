package com.pulsedesk.pulsedesk_backend.repository;

import com.pulsedesk.pulsedesk_backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository  extends JpaRepository<Ticket, Long> {
}
