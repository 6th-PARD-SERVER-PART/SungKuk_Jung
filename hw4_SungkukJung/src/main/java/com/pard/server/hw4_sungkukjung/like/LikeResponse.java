package com.pard.server.hw4_sungkukjung.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class LikeResponse {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ViewLiker { 
        private Long userId;
        private String username;
        private String userProfilePicture;
        private LocalDateTime dateLiked;
    }
}