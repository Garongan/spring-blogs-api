package com.alvindo.spring_blogs_api.entity;


import com.alvindo.spring_blogs_api.constant.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class AccountRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;
}
