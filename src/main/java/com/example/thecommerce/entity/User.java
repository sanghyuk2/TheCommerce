package com.example.thecommerce.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
    @Id
    private Long userid;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100, unique = true)
    private String nickname;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false, length = 15)
    private String phonenumber;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(name = "join_date", nullable = false)
    private LocalDateTime joinDate;

    @PrePersist
    protected void onCreate() {
        joinDate = LocalDateTime.now();
    }
}
