package com.pard.server.hw5_sungkukjung.post;

import com.pard.server.hw5_sungkukjung.comment.Comment;
import com.pard.server.hw5_sungkukjung.like.Like;
import com.pard.server.hw5_sungkukjung.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private String content;

    // path/URL로 업로드된 파일/이미지와 연결된 URL
    private String fileUploadUrl;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    private LocalDateTime dateModified;

    // 조회수; Lombok의 기본값 유지
    @Builder.Default
    private Integer viewsCount = 0;

    // 소유자; 무거운 그래프 로드 방지를 위해 LAZY
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    // 요청 DTO에서 부분 업데이트 적용
    public void updatePost(PostRequest.UpdatePost postUpdate) {
        this.title = postUpdate.getTitle() != null ? postUpdate.getTitle() : this.title;
        this.description = postUpdate.getDescription() != null ? postUpdate.getDescription() : this.description;
        this.content = postUpdate.getContent() != null ? postUpdate.getContent() : this.content;
    }

    public int getLikesCount() {
        return this.likes != null ? this.likes.size() : 0;
    }

    public int getCommentsCount() {
        return this.comments != null ? this.comments.size() : 0;
    }

    public void incrementViewsCount() {
        this.viewsCount = (this.viewsCount != null ? this.viewsCount : 0) + 1;
    }

    public void setFileUploadUrl(String fileUploadUrl) {
        this.fileUploadUrl = fileUploadUrl;
    }
}
