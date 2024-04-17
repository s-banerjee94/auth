package com.secu.auth.service;

import org.springframework.stereotype.Service;


public interface AuthService {
    String login(String username, String password);

    String signUp(String name, String name1, String password);
}
