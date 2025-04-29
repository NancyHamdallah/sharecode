package com.example.editorwithpayload.service.security;


import com.example.editorwithpayload.entity.Role;
import com.example.editorwithpayload.entity.User;
import com.example.editorwithpayload.exception.BaseException;
import com.example.editorwithpayload.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;

import java.util.Collections;

public class UserDetailsServiceCustom implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsCustom userDetailsCustom = getUserDetailsCustom(username);

        if(ObjectUtils.isEmpty(userDetailsCustom)){
            throw new UsernameNotFoundException("User not found");
        }
        return userDetailsCustom;
    }

    private UserDetailsCustom getUserDetailsCustom(String username){
        User user = userRepository.findByUsername(username);

        if(ObjectUtils.isEmpty(user)){
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST), "User not found");
        }
        Role role = user.getRoles(); // this is a single Role object
        return new UserDetailsCustom(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                Collections.singletonList(new SimpleGrantedAuthority(role.getName())), // this is a single Role object,
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isCredentialsNonExpired()
        );
    }
}
