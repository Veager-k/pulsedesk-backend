package com.pulsedesk.pulsedesk_backend.repository;

import com.pulsedesk.pulsedesk_backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
