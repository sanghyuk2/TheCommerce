package com.example.thecommerce.service;

import com.example.thecommerce.dto.UserReqDto;
import com.example.thecommerce.exception.AppException;
import com.example.thecommerce.exception.ErrorCode;
import com.example.thecommerce.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
