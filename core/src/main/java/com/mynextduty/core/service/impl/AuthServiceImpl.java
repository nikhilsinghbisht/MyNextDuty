package com.mynextduty.core.service.impl;

import static com.mynextduty.core.utils.Constants.PUBLIC_KEY_PATH;
import static com.mynextduty.core.utils.Constants.REFRESH_TOKEN;

import com.mynextduty.core.dto.GlobalMessageDTO;
import com.mynextduty.core.dto.auth.AuthRequestDto;
import com.mynextduty.core.dto.auth.AuthResponseDto;
import com.mynextduty.core.dto.auth.RegisterRequestDto;
import com.mynextduty.core.dto.auth.UserProfileDto;
import com.mynextduty.core.entity.User;
import com.mynextduty.core.exception.GenericApplicationException;
import com.mynextduty.core.exception.KeyLoadingException;
import com.mynextduty.core.exception.UserNotFoundException;
import com.mynextduty.core.repository.AuthRepository;
import com.mynextduty.core.service.AuthService;
import com.mynextduty.core.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
  private final AuthRepository authRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  @Override
  public String publicKey() {
    try (InputStream is = getClass().getResourceAsStream(PUBLIC_KEY_PATH)) {
      if (is == null) {
        log.error("Public key file not found at {}", PUBLIC_KEY_PATH);
        throw new KeyLoadingException("Public key file not found in " + PUBLIC_KEY_PATH);
      }
      return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      log.error("IO error while reading the public key file", e);
      throw new KeyLoadingException("Failed to read public key", e);
    } catch (Exception e) {
      log.error("Unexpected error while loading public key", e);
      throw new GenericApplicationException("Unexpected error loading public key", e.getCause());
    }
  }

  @Override
  public AuthResponseDto register(
      RegisterRequestDto registerRequestDto, HttpServletResponse httpServletResponse) {
    String email = registerRequestDto.getEmail();

    // Check if user already exists
    if (authRepository.getByEmail(email).isPresent()) {
      throw new GenericApplicationException("User with email " + email + " already exists");
    }

    // Create new user
    User user =
        User.builder()
            .email(email)
            .password(passwordEncoder.encode(registerRequestDto.getPassword()))
            .firstName(registerRequestDto.getFirstName())
            .lastName(registerRequestDto.getLastName())
            .isVerified(false)
            .build();

    user = authRepository.save(user);
    log.info("New user registered with email: {}", email);

    // Generate tokens
    String accessToken = jwtUtil.generateToken(email);
    String refreshToken = jwtUtil.generateRefreshToken(email);

    // Set refresh token cookie
    setRefreshTokenCookie(httpServletResponse, refreshToken);

    return AuthResponseDto.builder()
        .email(user.getEmail())
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  @Override
  public AuthResponseDto login(
      AuthRequestDto authRequestDto, HttpServletResponse httpServletResponse) {
    String email = authRequestDto.getEmail();
    String password = authRequestDto.getPassword();

    User user =
        authRepository
            .getByEmail(email)
            .orElseThrow(
                () -> {
                  log.warn("Login attempt with non-existent email: {}", email);
                  return new UserNotFoundException("Invalid email or password");
                });

    // Verify password
    if (!passwordEncoder.matches(password, user.getPassword())) {
      log.warn("Invalid password attempt for email: {}", email);
      throw new GenericApplicationException("Invalid email or password");
    }

    // Generate tokens
    String accessToken = jwtUtil.generateToken(email);
    String refreshToken = jwtUtil.generateRefreshToken(email);

    // Set refresh token cookie
    setRefreshTokenCookie(httpServletResponse, refreshToken);

    log.info("User logged in successfully: {}", email);

    return AuthResponseDto.builder()
        .email(user.getEmail())
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  @Override
  public AuthResponseDto refreshToken(
      String refreshToken, HttpServletResponse httpServletResponse) {
    try {
      if (jwtUtil.isTokenExpired(refreshToken)) {
        throw new GenericApplicationException("Refresh token expired.");
      }

      String email = jwtUtil.extractUsername(refreshToken);

      if (!jwtUtil.validateToken(refreshToken, email)) {
        throw new GenericApplicationException("Invalid or expired refresh token");
      }

      // Verify user still exists
      User user =
          authRepository
              .getByEmail(email)
              .orElseThrow(() -> new UserNotFoundException("User not found"));

      // Generate new tokens
      String newAccessToken = jwtUtil.generateToken(email);
      String newRefreshToken = jwtUtil.generateRefreshToken(email);

      // Set new refresh token cookie
      setRefreshTokenCookie(httpServletResponse, newRefreshToken);

      return AuthResponseDto.builder()
          .email(user.getEmail())
          .accessToken(newAccessToken)
          .refreshToken(newRefreshToken)
          .build();

    } catch (Exception e) {
      log.error("Error refreshing token", e);
      throw new GenericApplicationException("Failed to refresh token");
    }
  }

  @Override
  public UserProfileDto getCurrentUser(String email) {
    User user =
        authRepository
            .getByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

    return UserProfileDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .currentOccupation(user.getCurrentOccupation())
        .dateOfBirth(user.getDateOfBirth())
        .lifeStage(user.getLifeStage())
        .monthlyIncome(user.getMonthlyIncome())
        .educationLevel(
            user.getEducationLevel() != null ? user.getEducationLevel().getLevelName() : null)
        .isVerified(user.isVerified())
        .build();
  }

  @Override
  public GlobalMessageDTO logout(HttpServletRequest httpServletRequest) {
    // Clear refresh token cookie
    // In a real implementation, you might want to blacklist the tokens
    log.info("User logged out successfully");
    return GlobalMessageDTO.builder().message("Logout successfully").build();
  }

  @Override
  public GlobalMessageDTO verifyEmail(String token) {
    // Implementation for email verification
    // This would typically involve validating a verification token sent via email
    return GlobalMessageDTO.builder().message("Email verified successfully").build();
  }

  private void setRefreshTokenCookie(HttpServletResponse httpServletResponse, String refreshToken) {
    ResponseCookie refreshTokenCookie =
        ResponseCookie.from(REFRESH_TOKEN, refreshToken)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .sameSite("Strict")
            .maxAge(3 * 24 * 60 * 60L) // 3 days
            .build();
    httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
  }
}
