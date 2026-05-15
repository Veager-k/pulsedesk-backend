package com.pulsedesk.pulsedesk_backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private LocalDateTime createdAt;

    @JsonManagedReference
    @Getter
    @Setter
    @OneToOne(mappedBy = "comment", cascade = CascadeType.ALL)
    private Ticket ticket;
}
