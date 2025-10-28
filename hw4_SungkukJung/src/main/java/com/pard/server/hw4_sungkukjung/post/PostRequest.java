package com.pard.server.hw4_sungkukjung.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostRequest {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class NewPost { // 게시글 생성
        private Long userId;
        private String title;
        private String description;
        private String content;
        private String fileUploadUrl;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdatePost { // 업데이트
        private String title;
        private String description;
        private String content;
        private String fileUploadUrl;
    }
}
