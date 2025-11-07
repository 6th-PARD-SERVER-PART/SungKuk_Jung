package com.pard.server.hw5_sungkukjung.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository  extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByDateCommentedAsc(Long postId);
}