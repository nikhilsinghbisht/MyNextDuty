package com.mynextduty.core.controller;

import com.mynextduty.core.dto.GlobalMessageDTO;
import com.mynextduty.core.dto.ResponseDto;
import com.mynextduty.core.dto.SuccessResponseDto;
import com.mynextduty.core.dto.auth.AuthRequestDto;
import com.mynextduty.core.dto.auth.AuthResponseDto;
import com.mynextduty.core.dto.auth.RegisterRequestDto;
import com.mynextduty.core.dto.auth.UserProfileDto;
import com.mynextduty.core.exception.GenericApplicationException;
import com.mynextduty.core.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.mynextduty.core.utils.Constants.REFRESH_TOKEN;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @GetMapping("/public-key")
  public ResponseDto<String> publicKey() {
    return new SuccessResponseDto<>(authService.publicKey());
  }

  @PostMapping("/register")
  public ResponseDto<AuthResponseDto> register(
      @Valid @RequestBody RegisterRequestDto registerDto, HttpServletResponse response) {
    return new SuccessResponseDto<>(authService.register(registerDto, response));
  }

  @PostMapping("/login")
  public ResponseDto<AuthResponseDto> login(
      @Valid @RequestBody AuthRequestDto loginDto, HttpServletResponse response) {
    return new SuccessResponseDto<>(authService.login(loginDto, response));
  }

  @PostMapping("/refresh")
  public ResponseDto<AuthResponseDto> refreshToken(
      HttpServletRequest request, HttpServletResponse response) {
    String refreshToken = getRefreshTokenFromCookies(request);
    return new SuccessResponseDto<>(authService.refreshToken(refreshToken, response));
  }

  @GetMapping("/me")
  public ResponseDto<UserProfileDto> getCurrentUser(Authentication authentication) {
    String email = authentication.getName();
    return new SuccessResponseDto<>(authService.getCurrentUser(email));
  }

  @PostMapping("/logout")
  public ResponseDto<GlobalMessageDTO> logout(HttpServletRequest request) {
    return new SuccessResponseDto<>(authService.logout(request));
  }

  @PostMapping("/verify-email")
  public ResponseDto<GlobalMessageDTO> verifyEmail(@RequestParam String token) {
    return new SuccessResponseDto<>(authService.verifyEmail(token));
  }

  private String getRefreshTokenFromCookies(HttpServletRequest request) {
    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        if (REFRESH_TOKEN.equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    throw new GenericApplicationException("Refresh token not found");
  }
}
