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

import java.util.List;

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
        log.info("info log = {}", "유저 저장 완료");
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
}
