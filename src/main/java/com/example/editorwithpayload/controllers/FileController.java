package com.example.editorwithpayload.controllers;

import com.example.editorwithpayload.entity.File;
import com.example.editorwithpayload.entity.User;
import com.example.editorwithpayload.service.FileService;
import com.example.editorwithpayload.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    public FileController(FileService fileService, UserService userService){
        this.fileService = fileService;
        this.userService = userService;

    }
    @PostMapping("/create")
    public String createFile(@RequestBody String fileId){

        File newFile = new File();
        newFile.setId(fileId);
        String email = userService.getEmail();
        System.out.println("email:"+email);
        newFile.setCreatedBy(email);

        fileService.createFile(newFile);
        return "editor";

    }
/*
    @GetMapping("/retrieve")
    public String getUserFiles(Model model){
        System.out.println("Files:"+fileService.findUserFiles());
        List<File> files = fileService.findUserFiles();;
        model.addAttribute("files",files);
        return "editor";
    }

 */
}
