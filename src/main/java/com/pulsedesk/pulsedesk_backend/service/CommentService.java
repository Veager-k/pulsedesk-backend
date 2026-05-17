package com.pulsedesk.pulsedesk_backend.service;

import com.pulsedesk.pulsedesk_backend.dto.TicketAnalysisResult;
import com.pulsedesk.pulsedesk_backend.entity.Comment;
import com.pulsedesk.pulsedesk_backend.entity.Ticket;
import com.pulsedesk.pulsedesk_backend.repository.CommentRepository;
import com.pulsedesk.pulsedesk_backend.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TicketService ticketService;
    private final AIAnalysisService aiAnalysisService;

    public CommentService(
            CommentRepository commentRepository,
            TicketService ticketService,
            AIAnalysisService aiAnalysisService
    ) {
        this.commentRepository = commentRepository;
        this.ticketService = ticketService;
        this.aiAnalysisService = aiAnalysisService;
    }

    @Transactional
    public Comment createComment(String content) {

        Comment comment = new Comment();

        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        TicketAnalysisResult analysis =
                aiAnalysisService.analyzeComment(content);

        if (analysis.isShouldCreateTicket()) {

            Ticket savedTicket = ticketService.createTicket(
                    analysis,
                    savedComment
            );

            savedComment.setTicket(savedTicket);
        }

        return commentRepository.save(savedComment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }
}
