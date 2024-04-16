package com.secu.auth.controller;

import com.secu.auth.models.AuthRequestDto;
import com.secu.auth.models.AuthResponseDto;
import com.secu.auth.constant.AuthStatus;
import com.secu.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private AuthService authService;



    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        String jwtToken = authService.login(authRequestDto.username(), authRequestDto.password());

        AuthResponseDto authResponseDto = new AuthResponseDto(jwtToken, AuthStatus.LOGIN_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.OK).body(authResponseDto);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDto> signUp(@RequestBody AuthRequestDto authRequestDto) {
        try {
            String jwtToken = authService.signUp(authRequestDto.name(), authRequestDto.username(), authRequestDto.password());
            AuthResponseDto authResponseDto = new AuthResponseDto(jwtToken, AuthStatus.USER_CREATED_SUCCESSFULLY);

            return ResponseEntity.status(HttpStatus.OK).body(authResponseDto);
        } catch (Exception e) {
            AuthResponseDto authResponseDto = new AuthResponseDto(null, AuthStatus.USER_NOT_CREATED);

            return ResponseEntity.status(HttpStatus.CONFLICT).body(authResponseDto);
        }
    }
}
