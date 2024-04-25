package com.example.thecommerce.dto;

import com.example.thecommerce.entity.User;
import lombok.Data;

@Data
public class UserReqDto {
    private Long userid;
    private String password;
    private String nickname;
    private String username;
    private String phonenumber;
    private String email;

    public User toEntity() {
        return User
                .builder()
                .userid(this.userid)
                .password(this.password)
                .nickname(this.nickname)
                .username(this.username)
                .phonenumber(this.phonenumber)
                .email(this.email)
                .build();
    }
}
