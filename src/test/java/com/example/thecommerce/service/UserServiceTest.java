package com.example.thecommerce.service;

import com.example.thecommerce.controller.UserController;
import com.example.thecommerce.dto.UserReqDto;
import com.example.thecommerce.entity.User;
import com.example.thecommerce.exception.AppException;
import com.example.thecommerce.exception.ErrorCode;
import com.example.thecommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserController userController;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("첫페이지 유저 두 명 출력")
    public void 첫페이지_유저_두명() {
        // 테스트용 유저 데이터 생성
        User user1 = User.builder()
                .userid("userid1")
                .password("password1")
                .nickname("이")
                .username("이")
                .phonenumber("1234567890")
                .email("user1@example.com")
                .joinDate(LocalDateTime.now())
                .build();
        User user2 = User.builder()
                .userid("userid2")
                .password("password2")
                .nickname("상")
                .username("상")
                .phonenumber("1234567891")
                .email("user2@example.com")
                .joinDate(LocalDateTime.now().minusDays(1))
                .build();
        User user3 = User.builder()
                .userid("userid3")
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

    @Test
    public void testUpdateUser_UserNotFound() {
        // 존재하지 않는 사용자 아이디를 사용하여 updateUser 메서드 호출
        AppException exception = assertThrows(AppException.class, () -> userService.updateUser("999", new UserReqDto()));

        // 예상되는 결과와 비교
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("업데이트 내용 없음")
    public void 업데이트_없음() {
        // 사용자 데이터를 저장
        User user = User.builder()
                .userid("userid")
                .password("password")
                .nickname("nickname")
                .username("username")
                .phonenumber("1234567890")
                .email("user@example.com")
                .joinDate(LocalDateTime.now())
                .build();

        userRepository.save(user);

        UserReqDto noUpdatedUserReqDto = new UserReqDto();
        noUpdatedUserReqDto.setPassword("password");
        noUpdatedUserReqDto.setNickname("nickname");
        noUpdatedUserReqDto.setUserid("userid");
        noUpdatedUserReqDto.setPhonenumber("1234567890");
        noUpdatedUserReqDto.setEmail("user@example.com");
        noUpdatedUserReqDto.setUsername("username");

        // 변경사항이 없는 데이터로 updateUser 메서드 호출
        String result = userService.updateUser("userid", noUpdatedUserReqDto);

        // 예상되는 결과와 비교
        assertEquals("변경된 내용이 없습니다.", result);
    }

    @Test
    @DisplayName("비밀번호, 닉네임 업데이트")
    public void 업데이트_있음() {
        // 사용자 데이터를 저장
        User user = User.builder()
                .userid("userid")
                .password("password")
                .nickname("nickname")
                .username("username")
                .phonenumber("1234567890")
                .email("user@example.com")
                .joinDate(LocalDateTime.now())
                .build();
        userRepository.save(user);

        // 변경사항이 있는 데이터로 updateUser 메서드 호출
        UserReqDto updatedUserReqDto = new UserReqDto();
        updatedUserReqDto.setPassword("newPassword");
        updatedUserReqDto.setNickname("newNickname");
        updatedUserReqDto.setUserid("userid");
        updatedUserReqDto.setPhonenumber("1234567890");
        updatedUserReqDto.setEmail("user@example.com");
        updatedUserReqDto.setUsername("username");

        String result = userService.updateUser("userid", updatedUserReqDto);

        // 예상되는 결과와 비교
        assertEquals("password, nickname 가(이) 변경되었습니다.", result);
    }

    @Test
    @DisplayName("잘못된 형태의 DTO로 회원가입 요청 - 유효하지 않은 이메일 형식")
    public void 회원가입_잘못된_이메일_형식() {
        // 유효하지 않은 이메일 주소를 가진 가짜 요청 DTO 생성
        UserReqDto userReqDto = new UserReqDto();
        userReqDto.setUserid("userid");
        userReqDto.setPassword("password");
        userReqDto.setNickname("nickname");
        userReqDto.setUsername("username");
        userReqDto.setPhonenumber("3456789012");
        userReqDto.setEmail("invalid_email_format"); // 유효하지 않은 이메일 형식

        // 예상한 HttpStatus가 반환되는지 확인
        assertThrows(RuntimeException.class, () -> {
            userController.join(userReqDto);
        });
    }
}