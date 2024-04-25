package com.example.thecommerce.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
}
