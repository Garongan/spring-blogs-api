package com.alvindo.spring_blogs_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Creator {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "avatar_url", nullable = false)
    private String avatarUrl;

    @OneToMany(mappedBy = "creator")
    private List<Comment> comments;

    @OneToMany(mappedBy = "creator")
    private List<Blog> blogs;
}
