package com.example.editorwithpayload.service.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Data
@Component
public class UserDetailsCustom implements UserDetails {

    private String username;

    private String password;

    private String email;

    private List<GrantedAuthority> authorities;

    private boolean isEnabled;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;


    public UserDetailsCustom(String username, String password,String email, List<GrantedAuthority> authorities,
                             boolean isEnabled, boolean accountNonExpired, boolean accountNonLocked,
                             boolean credentialsNonExpired){
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
        this.isEnabled = isEnabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
