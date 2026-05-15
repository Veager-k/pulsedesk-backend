package com.pulsedesk.pulsedesk_backend.controller;

import com.pulsedesk.pulsedesk_backend.dto.CreateCommentRequest;
import com.pulsedesk.pulsedesk_backend.entity.Comment;
import com.pulsedesk.pulsedesk_backend.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Comment createComment(@RequestBody CreateCommentRequest request) {

        return commentService.createComment(request.getContent());
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }
}
