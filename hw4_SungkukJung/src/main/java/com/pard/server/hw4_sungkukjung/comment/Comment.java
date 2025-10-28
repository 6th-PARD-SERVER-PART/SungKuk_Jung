package com.pard.server.hw4_sungkukjung.comment;

import com.pard.server.hw4_sungkukjung.post.Post;
import com.pard.server.hw4_sungkukjung.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter @Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder  
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String content;

    @CreationTimestamp
    private LocalDateTime dateCommented;

    @UpdateTimestamp
    private LocalDateTime dateModified;

    // 댓글 대상 게시글 (무거운 조인 방지를 위해 LAZY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 내용 필드 업데이트
    public void updateComment(CommentRequest.UpdateComment commentUpdate) {
        this.content = commentUpdate.getContent() != null ? commentUpdate.getContent() : this.content;
    }
}