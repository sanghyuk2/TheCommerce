package com.example.thecommerce.controller;

import com.example.thecommerce.dto.UserReqDto;
import com.example.thecommerce.repository.UserRepository;
import com.example.thecommerce.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @Test
    @DisplayName("회원가입 성공")
    public void 회원가입() {
        // 가짜 UserRepository 생성
        UserRepository userRepository = mock(UserRepository.class);

        // UserService에 가짜 UserRepository 주입
        UserService userService = new UserService(userRepository);
        // 테스트할 UserController 생성
        UserController userController = new UserController(userService);

        // 가짜 요청 DTO 생성
        UserReqDto userReqDto = new UserReqDto();
        userReqDto.setUserid(1L);
        userReqDto.setPassword("password");
        userReqDto.setNickname("nickname");
        userReqDto.setUsername("username");
        userReqDto.setNickname("phonenumber");
        userReqDto.setEmail("email");

        ResponseEntity<String> response = userController.join(userReqDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("회원 가입 성공", response.getBody());
        verify(userRepository, times(1)).findByUserid(1L);
    }
}