package com.example.editorwithpayload.controllers;

import com.example.editorwithpayload.entity.File;
import com.example.editorwithpayload.service.FileService;
import com.example.editorwithpayload.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/editor")
public class EditorController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    public EditorController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/home")
    public String homePage(Model model, Principal principal){
        System.out.println("Principal details: "+principal.toString());
        System.out.println("username: " + userService.getLoggedInUserName());

        List<File> userFiles = fileService.findUserFiles();
        model.addAttribute("userfiles",userFiles );
        model.addAttribute("username",userService.getLoggedInUserName() );

        return "editor";
    }


}
