package com.pard.server.hw5_sungkukjung.user;

import com.pard.server.hw5_sungkukjung.comment.Comment;
import com.pard.server.hw5_sungkukjung.like.Like;
import com.pard.server.hw5_sungkukjung.post.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // path/URL로 사용자 프로필 사진 저장
    private String profilePicture;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @Enumerated(EnumType.STRING) // 이거 없으면 admin(0)
    @Builder.Default
    private  Role role = Role.USER;

    private String socialID; // 구글에서 할당해주는 사용자 고유의 아이디

    // user 업데이트 
    public void updateUser(UserRequest.UserUpdate userUpdate) {
        this.profilePicture = userUpdate.getProfilePicture() != null ? userUpdate.getProfilePicture() : this.profilePicture;
        this.name = userUpdate.getName() != null ? userUpdate.getName() : this.name;
        this.email = userUpdate.getEmail() != null ? userUpdate.getEmail() : this.email;
        this.password = userUpdate.getPassword() != null ? userUpdate.getPassword() : this.password;
    }

    public int getPostsCount() {
        return this.posts != null ? this.posts.size() : 0;
    }

    public int getLikesCount() {
        return this.likes != null ? this.likes.size() : 0;
    }

    public int getCommentsCount() {
        return this.comments != null ? this.comments.size() : 0;
    }
}
