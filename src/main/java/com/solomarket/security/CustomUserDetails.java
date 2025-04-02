package com.solomarket.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private final String userId;
    private final String password;
    private final String role; // ✅ 역할 추가
    private final String nickname;
    private final String userImage;


    public CustomUserDetails(String userId, String password, String role, String nickname, String userImage) {
        this.userId = userId;
        this.password = password;
        this.role = role;
        this.nickname = nickname;
        this.userImage = userImage;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role)); // ✅ GrantedAuthority 형태로 반환
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public String getNickname() {return nickname;}

    public String getUserImage() {return userImage;}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
