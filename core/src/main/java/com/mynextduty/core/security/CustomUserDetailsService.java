package com.mynextduty.core.security;

import com.mynextduty.core.dto.auth.CustomUserDetails;
import com.mynextduty.core.entity.User;
import com.mynextduty.core.exception.UserNotFoundException;
import com.mynextduty.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) {
    User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(
                () ->
                    new UserNotFoundException(
                        "User not found: " + email));
    return CustomUserDetails.builder()
        .username(user.getEmail())
        .password(user.getPassword())
        .build();
  }
}
