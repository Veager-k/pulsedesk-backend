package com.pulsedesk.pulsedesk_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String category;

    @Getter
    @Setter
    private String priority;

    @Getter
    @Setter
    private String summary;

    @Getter
    @Setter
    private LocalDateTime createdAt;

    @JsonBackReference
    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
