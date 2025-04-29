package com.example.editorwithpayload.controllers;

import com.example.editorwithpayload.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/viewer")
public class ViewerController {

    @Autowired
    private UserService userService;
    public ViewerController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/home")

    public String homePage(Model model, Principal principal){

        model.addAttribute("username",userService.getLoggedInUserName() );
        return "viewer";
    }


}