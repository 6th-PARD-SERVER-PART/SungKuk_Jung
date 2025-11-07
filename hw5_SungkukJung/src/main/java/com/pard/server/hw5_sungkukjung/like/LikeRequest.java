package com.pard.server.hw5_sungkukjung.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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