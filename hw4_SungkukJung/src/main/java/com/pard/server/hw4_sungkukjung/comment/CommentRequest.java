package com.pard.server.hw4_sungkukjung.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Request DTOs for comment opeations
public class CommentRequest {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateComment { 
        private Long userId;
        private Long postId;
        private String content; 
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateComment { 
        private String content; 
    }
}