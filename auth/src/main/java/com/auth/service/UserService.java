package com.auth.service;

import com.auth.model.UserEntity;
import com.auth.model.dto.CreateUserDto;
import com.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity createUser(CreateUserDto dto) {
        UserEntity entity = UserEntity.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(this.passwordEncoder.encode(dto.getPassword()))
                .build();
        return this.userRepository.save(entity);
    }
}
