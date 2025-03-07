package com.secu.auth.service;

import com.secu.auth.models.AppUser;
import com.secu.auth.repo.AppUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    public final AppUserRepo appUserRepo;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> optionalAppUser = appUserRepo.findByUsername(username);
        AppUser appUser = optionalAppUser.orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(appUser.getUsername(), appUser.getPassword(), appUser.getAuthorities());
    }
}
