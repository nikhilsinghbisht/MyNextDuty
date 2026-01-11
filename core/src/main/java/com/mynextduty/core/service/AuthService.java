package com.mynextduty.core.service;

import com.mynextduty.core.dto.GlobalMessageDTO;
import com.mynextduty.core.dto.auth.AuthRequestDto;
import com.mynextduty.core.dto.auth.AuthResponseDto;
import com.mynextduty.core.dto.auth.RegisterRequestDto;
import com.mynextduty.core.dto.auth.UserProfileDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
  String publicKey();

  AuthResponseDto login(AuthRequestDto authRequestDto, HttpServletResponse httpServletResponse);

  AuthResponseDto register(
      RegisterRequestDto registerRequestDto, HttpServletResponse httpServletResponse);

  AuthResponseDto refreshToken(String refreshToken, HttpServletResponse httpServletResponse);

  UserProfileDto getCurrentUser(String email);

  GlobalMessageDTO logout(HttpServletRequest httpServletRequest);

  GlobalMessageDTO verifyEmail(String token);
}
