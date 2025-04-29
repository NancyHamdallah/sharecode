package com.example.editorwithpayload.service;


import com.example.editorwithpayload.entity.User;
import com.example.editorwithpayload.request.UserDTO;
import com.example.editorwithpayload.response.BaseResponse;

public interface UserService {

    BaseResponse registerAccount(UserDTO userDTO);
    String getRoleNameByUsername(String username);
    String getLoggedInUserName();
    String getUserId();
    String getEmail();
}
