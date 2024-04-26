package com.example.thecommerce.service;

import com.example.thecommerce.dto.UserReqDto;
import com.example.thecommerce.entity.User;
import com.example.thecommerce.exception.AppException;
import com.example.thecommerce.exception.ErrorCode;
import com.example.thecommerce.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(UserReqDto userReqDto) {
        userRepository.findByUserid(userReqDto.getUserid())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USER_ALREADY_EXISTS, "이미 가입된 사용자 입니다.");
                });

        userRepository.save(userReqDto.toEntity());
        log.info("회원가입 = {}", "유저 회원가입 완료");
    }

    public List<User> getUserList(int page, int pageSize, String sort) {
        Sort.Order order;

        if (sort.equals("joinDate")) {
            order = Sort.Order.asc("joinDate");
        } else {
            order = Sort.Order.asc("username");
        }

        Sort sorting = Sort.by(order);

        // 페이징 및 정렬 적용하여 회원 목록 조회
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, sorting);
        Page<User> userPage = userRepository.findAll(pageRequest);

        log.info("조회된 사용자 목록 (페이지 {}):", userPage.getNumber() + 1);
        for (User user : userPage.getContent()) {
            log.info("사용자 이름: {}", user.getUsername());
        }

        return userPage.getContent();
    }

    public String updateUser(String userid, UserReqDto userReqDto) {
        Optional<User> optionalUser = userRepository.findByUserid(Long.parseLong(userid));

        List<String> changedColumns = new ArrayList<>();

        // 회원 업데이트
        optionalUser.ifPresent(user -> {
            User updatedUser = User.builder()
                    .userid(userReqDto.getUserid())
                    .password(userReqDto.getPassword())
                    .nickname(userReqDto.getNickname())
                    .username(userReqDto.getUsername())
                    .phonenumber(userReqDto.getPhonenumber())
                    .email(userReqDto.getEmail())
                    .joinDate(user.getJoinDate())
                    .updatedDate(LocalDateTime.now())
                    .build();

            // 변경된 컬럼 확인
            if (!user.getPassword().equals(updatedUser.getPassword())) {
                changedColumns.add("password");
            }
            if (!user.getNickname().equals(updatedUser.getNickname())) {
                changedColumns.add("nickname");
            }
            if (!user.getUsername().equals(updatedUser.getUsername())) {
                changedColumns.add("username");
            }
            if (!user.getPhonenumber().equals(updatedUser.getPhonenumber())) {
                changedColumns.add("phonenumber");
            }
            if (!user.getEmail().equals(updatedUser.getEmail())) {
                changedColumns.add("email");
            }

            userRepository.save(updatedUser);
        });

        if (!optionalUser.isPresent()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다.");
        }

        log.info("업데이트 = {}", "유저 업데이트 완료");

        String changedColumnsString = String.join(", ", changedColumns);

        return changedColumnsString.isEmpty() ? "변경된 내용이 없습니다." : changedColumnsString + " 가(이) 변경되었습니다.";
    }
}
