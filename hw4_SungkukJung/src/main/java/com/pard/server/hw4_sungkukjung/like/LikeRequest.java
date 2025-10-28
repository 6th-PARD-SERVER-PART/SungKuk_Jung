package com.pard.server.hw4_sungkukjung.like;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

public class LikeRequest {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LikeAction { 
        private Long userId;
        private Long postId;
    }
}