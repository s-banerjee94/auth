package com.secu.auth.service;

import com.secu.auth.models.AppUser;
import com.secu.auth.repo.AppUserRepo;
import com.secu.auth.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private AppUserRepo appUserRepo;

    public AuthServiceImpl(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return JwtUtils.generateToken(((UserDetails)authenticate.getPrincipal()).getUsername());
    }

    @Override
    public String signUp(String name, String username, String password) {
        if (appUserRepo.existsByUsername(username)){
            throw new RuntimeException("User exist!");
        }
        String encode = passwordEncoder.encode(password);

        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        AppUser user = AppUser.builder().name(name).username(username).password(encode).authorities(authorities).build();
        appUserRepo.save(user);

        return JwtUtils.generateToken(username);
    }
}
