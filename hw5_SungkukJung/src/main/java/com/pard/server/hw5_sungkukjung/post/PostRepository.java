package com.pard.server.hw5_sungkukjung.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByDateCreatedDesc(); // 모든 게시글 최신순 목록 조회
    List<Post> findByUserUsernameOrderByDateCreatedDesc(String username); // 사용자 게시글 최신순 목록 조회
}
