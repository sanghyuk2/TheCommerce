package com.example.thecommerce.service;

import com.example.thecommerce.entity.User;
import com.example.thecommerce.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void 첫페이지_유저_두명() {
        // 테스트용 유저 데이터 생성
        User user1 = User.builder()
                .userid(1L)
                .password("password1")
                .nickname("이")
                .username("이")
                .phonenumber("1234567890")
                .email("user1@example.com")
                .joinDate(LocalDateTime.now())
                .build();
        User user2 = User.builder()
                .userid(2L)
                .password("password2")
                .nickname("상")
                .username("상")
                .phonenumber("1234567891")
                .email("user2@example.com")
                .joinDate(LocalDateTime.now().minusDays(1))
                .build();
        User user3 = User.builder()
                .userid(3L)
                .password("password3")
                .nickname("혁")
                .username("혁")
                .phonenumber("1234567892")
                .email("user3@example.com")
                .joinDate(LocalDateTime.now().minusDays(2))
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // getUserList 메서드 호출
        List<User> result = userService.getUserList(1, 2, "joinDate");

        // 예상되는 결과와 비교
        assertEquals(2, result.size());
    }
}