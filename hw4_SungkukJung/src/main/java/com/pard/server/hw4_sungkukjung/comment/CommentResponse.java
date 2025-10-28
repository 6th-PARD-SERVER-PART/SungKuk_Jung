package com.pard.server.hw4_sungkukjung.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CommentResponse {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ViewComment { 
        private Long id;
        private Long postId;
        private String content;
        private LocalDateTime dateCommented;
        private LocalDateTime dateModified;

        private String username; 
        private String userProfilePicture;
    }
}