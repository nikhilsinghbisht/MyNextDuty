package com.mynextduty.core.service;

import com.mynextduty.core.dto.GlobalMessageDto;
import com.mynextduty.core.dto.auth.AuthRequestDto;
import com.mynextduty.core.dto.auth.AuthResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
  String publicKey();

  AuthResponseDto login(AuthRequestDto authRequestDto, HttpServletResponse httpServletResponse);

  AuthResponseDto refreshToken(HttpServletRequest request, HttpServletResponse httpServletResponse);

  GlobalMessageDto logout(HttpServletRequest httpServletRequest);
}
