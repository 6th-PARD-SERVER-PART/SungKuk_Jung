package com.pard.server.hw4_sungkukjung.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PostResponse {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ViewPost { // 게시글 상세 뷰
        private Long id;
        private String username;
        private String userProfilePicture;
        private String title;
        private String description;
        private String content;
        private String fileUploadUrl;
        private LocalDateTime dateCreated;
        private LocalDateTime dateModified;
        private int likesCount;
        private int commentsCount;
        private int viewsCount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DashboardPostInfo { // 목록 페이지
        private Long id;
        private String title;
        private String description;
        private int likesCount;
        private int commentsCount;
        private int viewsCount;
    }
}
