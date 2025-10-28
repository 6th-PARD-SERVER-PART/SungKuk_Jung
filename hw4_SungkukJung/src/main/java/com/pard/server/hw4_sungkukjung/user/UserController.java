package com.pard.server.hw4_sungkukjung.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequiredArgsConstructor 
@RequestMapping("/user") 
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up") // 새로운 사용자 등록
    public ResponseEntity<?> signUp(@RequestBody UserRequest.UserSignUp signUpRequest) {
        try {
            userService.signUp(signUpRequest);
            return ResponseEntity.ok("User registered successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/log-in") // 기존 사용자 인증
    public ResponseEntity<?> logIn(@RequestBody UserRequest.UserLogIn logInRequest) {
        try {
            UserResponse.UserLogInResponse response = userService.logIn(logInRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{username}") // 사용자 이름으로 공개 프로필 세부 정보 조회
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        try {
            UserResponse.UserSearched user = userService.searchUser(username);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{username}/activity") // 활동 요약 (게시물/좋아요/댓글 수)
    public ResponseEntity<?> findUserActivity(@PathVariable String username) {
        try {
            UserResponse.UserActivity activity = userService.getUserActivity(username);
            return ResponseEntity.ok(activity);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/{id}") // 사용자 필드 부분 업데이트
    public void updateUserInfo(@PathVariable Long id, @RequestBody UserRequest.UserUpdate updateRequest) {
        userService.updateUserInfo(id, updateRequest);
    }

    @DeleteMapping("/{id}") // id로 사용자 삭제
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("") // 모든 사용자 목록 조회 
    public List<UserResponse.UserSearched> findAll() {
        return userService.getAllUsers();
    }
}
