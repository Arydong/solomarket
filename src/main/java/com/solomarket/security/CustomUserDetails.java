package com.solomarket.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * packageName    : com.solomarket.security
 * fileName       : CustomUserDetails
 * author         : 이동하
 * date           : 25. 3. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 3. 11.        이동하       최초 생성
 */
public class CustomUserDetails implements UserDetails {

    private final String userId;
    private final String password;
    private final String userName;
    private final String role;
    private final String nickName;

    public CustomUserDetails(final String userId, final String password, final String userName, final String role, final String nickName) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.role = role;
        this.nickName = nickName;
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
        return userName;
    }

    public String getUserId(){
        return userId;
    }

    public String getRole(){
        return role;
    }

    public String getNickName(){
        return nickName;
    }

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