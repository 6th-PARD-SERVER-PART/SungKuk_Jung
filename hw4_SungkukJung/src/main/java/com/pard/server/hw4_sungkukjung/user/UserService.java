package com.pard.server.hw4_sungkukjung.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service 
@RequiredArgsConstructor 
public class UserService {
    private final UserRepository userRepository;

    // 새로운 사용자 등록; 고유성 검증
    public void signUp(UserRequest.UserSignUp signUpRequest) {
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists.");
        }
        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered.");
        }

        User user = User.builder()
                .profilePicture(signUpRequest.getProfilePicture())
                .name(signUpRequest.getName())
                .username(signUpRequest.getUsername())
                .password(signUpRequest.getPassword())
                .email(signUpRequest.getEmail())
                .build();
        userRepository.save(user);
    }

    // 사용자 이름과  비밀번호로 인증 
    public UserResponse.UserLogInResponse logIn(UserRequest.UserLogIn loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Username not found."));
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new IllegalArgumentException("Password does not match.");
        }
        return UserResponse.UserLogInResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .message("Login Successful.")
                .build();

    }


    // 프로필 검색
    public UserResponse.UserSearched searchUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(("User not found with username: " + username)));
        return UserResponse.UserSearched.builder()
                .profilePicture(user.getProfilePicture())
                .name(user.getName())
                .username(user.getUsername())
                .postsCount(user.getPostsCount())
                .build();
    }

    // 대시보드에 집계된 활동 수
    public UserResponse.UserActivity getUserActivity(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(("User not found with username: " + username)));
        return UserResponse.UserActivity.builder()
                .username(user.getUsername())
                .postsCount(user.getPostsCount())
                .likesCount(user.getLikesCount())
                .commentsCount(user.getCommentsCount())
                .build();
    }

    @Transactional // 업데이트와 저장이 하나의 트랜잭션 내에서 발생하도록 보장
    public void updateUserInfo(Long userId, UserRequest.UserUpdate userUpdateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(("User not found with userId: " + userId)));
        user.updateUser(userUpdateRequest);
        userRepository.save(user);
    }

    // 사용자 삭제; 존재하지 않으면 실패
    public void deleteUser(Long userId) {
        if(!userRepository.existsById(userId)) {
            throw new RuntimeException(("User not found with userId: " + userId));
        }
        userRepository.deleteById(userId);
    }

    // 사용자 최신순 목록 조회
    public List<UserResponse.UserSearched> getAllUsers() {
        List<User> users = userRepository.findAllByOrderByIdDesc();
        return users.stream().map( user ->
                UserResponse.UserSearched.builder()
                        .profilePicture(user.getProfilePicture())
                        .name(user.getName())
                        .username(user.getUsername())
                        .postsCount(user.getPostsCount())
                        .build()
        ).toList();
    }
}
