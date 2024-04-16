package com.secu.auth.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;


@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    private String id;
    private String username;
    private String password;
    private String name;
    private List<GrantedAuthority> authorities;
}
