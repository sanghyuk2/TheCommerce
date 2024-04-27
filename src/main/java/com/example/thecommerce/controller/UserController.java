package com.example.thecommerce.controller;

import com.example.thecommerce.dto.UserReqDto;
import com.example.thecommerce.entity.User;
import com.example.thecommerce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@Valid @RequestBody UserReqDto userReqDto) {
        userService.join(userReqDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입 성공");
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> getUserList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "joinDate") String sort) {
        return ResponseEntity.ok().body(userService.getUserList(page, pageSize, sort));
    }

    @PutMapping("/{userid}")
    public ResponseEntity<String> updateUser(@PathVariable String userid, @Valid @RequestBody UserReqDto userReqDto) {
        String updatedColumns = userService.updateUser(userid, userReqDto);

        return ResponseEntity.ok().body(updatedColumns);
    }
}