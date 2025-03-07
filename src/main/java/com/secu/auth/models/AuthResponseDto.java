package com.secu.auth.models;


import com.secu.auth.constant.AuthStatus;

public record AuthResponseDto(String token, AuthStatus status) {}
