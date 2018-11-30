package com.example.manualproject.service;


import com.example.manualproject.model.Role;
import com.example.manualproject.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CustomUserDetails implements UserDetails {
    private User user;
    Set<GrantedAuthority> grantedAuthorities;

    public CustomUserDetails() {
    }

    public CustomUserDetails(User user, Set<GrantedAuthority> grantedAuthorities) {
        this.user = user;
        this.grantedAuthorities = grantedAuthorities;
    }

    public User getUser() {
        return user;
    }

    public long getId() {
        return user.getId();
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getName();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public Set<GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    public boolean isAccountNonLocked() {
        return !user.isBlocked();
    }

    public boolean isEnabled() {
        return user.isConfirmed();
    }

    public boolean isAdmin() {
        return user.isAdmin();
    }
}
