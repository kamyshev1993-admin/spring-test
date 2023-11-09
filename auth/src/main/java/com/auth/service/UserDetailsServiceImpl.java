package com.auth.service;

import com.auth.model.UserDetailsImpl;
import com.auth.model.UserEntity;
import com.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = this.userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found"));
        return new UserDetailsImpl(userEntity);
    }
}
