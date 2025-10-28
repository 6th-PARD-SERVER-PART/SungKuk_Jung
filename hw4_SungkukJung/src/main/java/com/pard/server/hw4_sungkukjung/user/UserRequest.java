package com.pard.server.hw4_sungkukjung.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequest {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserSignUp { // 새로운 사용자 생성에 필요한 필드
        private String profilePicture;
        private String name;
        private String username;
        private String password;
        private String email;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserLogIn { // 인증에 필요한 자격 증명
        private String username;
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserUpdate { // PATCH로 수정 가능한 사용자 필드
        private String profilePicture;
        private String name;
        private String email;// 선택적: 비밀번호 변경
        private String password; // 비밀번호 변경 시 사용
    }
}
