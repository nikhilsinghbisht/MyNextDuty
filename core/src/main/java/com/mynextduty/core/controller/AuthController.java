package com.mynextduty.core.controller;

import com.mynextduty.core.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/public-key")
    public ResponseEntity<String> publicKey() {
        return ResponseEntity.ok(authService.publicKey());
    }

//
//
//    @PostMapping("/logout")
//    public ResponseEntity<GlobalMessageDTO> logout(HttpServletRequest request) {
//        return ResponseEntity.ok(authService.logout(request));
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO loginDTO, HttpServletResponse response) {
//        AuthResponseDTO authResponse = authService.login(loginDTO);
//        ResponseCookie refreshTokenCookie = ResponseCookie.from(Constant.REFRESH_TOKEN, authResponse.getRefreshToken())
//                .httpOnly(true)
//                .secure(true)
//                .path("/")
//                .sameSite("Strict")
//                .maxAge(3 * 24 * 60 * 60L) // 3 days
//                .build();
//        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
//        authResponse.setRefreshToken(null);
//        return ResponseEntity.ok(authResponse);
//    }
}
