package com.example.thecommerce.controller;

import com.example.thecommerce.dto.UserReqDto;
import com.example.thecommerce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserReqDto userReqDto) {
        userService.join(userReqDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입 성공");
    }
}
