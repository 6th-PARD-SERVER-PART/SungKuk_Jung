package com.pard.server.hw5_sungkukjung.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class UserResponse {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserInfo { // 상세 프로필 
        private Long id;
        private String profilePicture;
        private String name;
        private String username;
        private String email;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserLogInResponse { // 성공적인 로그인 후 정보
        private Long id;
        private String username;
        private String name;
        private String message;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserSearched { // 검색 결과
        private String profilePicture;
        private String name;
        private String username;
        private int postsCount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserActivity { // 집계된 활동 수
        private String username;
        private int postsCount;
        private int likesCount;
        private int commentsCount;
    }
}
