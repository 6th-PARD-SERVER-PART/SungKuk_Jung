package com.pard.server.hw4_sungkukjung.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // 로그인/프로필 조회
    Optional<User> findByEmail(String email); // 등록 고유성 확인
    List<User> findAllByOrderByIdDesc(); // 최신순 목록 조회
}
