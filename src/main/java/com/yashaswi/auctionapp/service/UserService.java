package com.yashaswi.auctionapp.service;

import com.yashaswi.auctionapp.dto.user.UserCreationDto;
import com.yashaswi.auctionapp.dto.user.UserResponseDto;
import com.yashaswi.auctionapp.entity.User;
import com.yashaswi.auctionapp.exception.UserAlreadyExistsException;
import com.yashaswi.auctionapp.mapper.EntityToDtoMapper;
import com.yashaswi.auctionapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto createNewUser(UserCreationDto userCreationDto) {
        if (userRepository.findByUsername(userCreationDto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        User user = User.builder().username(userCreationDto.getUsername()).password(userCreationDto.getPassword()).email(userCreationDto.getEmail()).build();

        userRepository.save(user);
        return EntityToDtoMapper.toDto(user);
    }
}
