package com.example.editorwithpayload.service.impl;

import com.example.editorwithpayload.entity.Role;
import com.example.editorwithpayload.entity.User;
import com.example.editorwithpayload.exception.BaseException;
import com.example.editorwithpayload.repository.RoleRepository;
import com.example.editorwithpayload.repository.UserRepository;
import com.example.editorwithpayload.request.UserDTO;
import com.example.editorwithpayload.response.BaseResponse;
import com.example.editorwithpayload.service.UserService;
import com.example.editorwithpayload.service.security.UserDetailsCustom;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@AllArgsConstructor
@Service
class UserServiceImpl implements UserService {

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserDetailsCustom userDetailsCustom;


    @Override
    public BaseResponse registerAccount(UserDTO userDTO) {
        BaseResponse response = new BaseResponse();

        //validate data from client
        validateAccount(userDTO);

        User user = insertUser(userDTO);

        try {
            userRepository.save(user);
            response.setCode(String.valueOf(HttpStatus.CREATED.value()));
            response.setMessage("Register account successfully!!!");
        }catch (Exception e){
            response.setCode(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()));
            response.setMessage("Service Unavailable");
            //throw new BaseException(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), "Service Unavailable");
        }
        return response;
    }

    private User insertUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        user.setRoles(roleRepository.findByName(userDTO.getRole()));

        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        return user;
    }

    private void validateAccount(UserDTO userDTO){
        if(ObjectUtils.isEmpty(userDTO)){
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Request data not found!");
        }

        try {
            if(!ObjectUtils.isEmpty(userDTO.checkProperties())){
                throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Request data not found!");
            }
        }catch (IllegalAccessException e){
            throw new BaseException(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), "Service Unavailable");
        }

        List<String> roles = roleRepository.findAll().stream().map(Role::getName).toList();

        if(!roles.contains(userDTO.getRole())){
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Invalid role");
        }

        User user = userRepository.findByUsername(userDTO.getUsername());

        if(!ObjectUtils.isEmpty(user)){
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "User had existed!!!");
        }

    }

    public String getRoleNameByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user.getRoles() != null) {
            return user.getRoles().getName();
        } else {
            return "No Role Assigned";
        }
    }

    public String getLoggedInUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof OidcUser oidcUser) {
            // Google OAuth
            return oidcUser.getFullName(); // or oidcUser.getAttribute("name")
        } else if (principal instanceof OAuth2User oAuth2User) {
            // GitHub OAuth
            return oAuth2User.getAttribute("login");
        } else if (principal instanceof UserDetails userDetails) {
            // Custom login (username + password)
            return userDetails.getUsername();
        } else if (principal instanceof String) {
            // In some cases, the principal might be a string (e.g. "anonymousUser")
            return (String) principal;
        }

        return null;
    }

    public String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof OidcUser oidcUser) {
            // Google OAuth
            return userRepository.findByUsername(oidcUser.getName()).getId();
        } else if (principal instanceof OAuth2User oAuth2User) {
            // GitHub OAuth
            return oAuth2User.getAttribute("login");
        } else if (principal instanceof UserDetails userDetails) {
            // Custom login (username + password)
            return userDetails.getUsername();
        } else if (principal instanceof String) {
            // In some cases, the principal might be a string (e.g. "anonymousUser")
            return (String) principal;
        }

        return null;
    }

    @Override
    public String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        System.out.println((principal.toString()));
        if (principal instanceof OidcUser oidcUser) {
            // Google OAuth
            return oidcUser.getEmail();
        } else if (principal instanceof OAuth2User oAuth2User) {
            // GitHub OAuth
            return oAuth2User.getAttribute("login");
        } else if (principal instanceof UserDetailsCustom userDetails) {
            // Custom login (username + password)
            System.out.println("userDetailsInfo"+((UserDetailsCustom) principal).getEmail());
            return ((UserDetailsCustom) principal).getEmail();
        } else if (principal instanceof String) {
            // In some cases, the principal might be a string (e.g. "anonymousUser")
            return (String) principal;
        }


        return "";
    }

}
