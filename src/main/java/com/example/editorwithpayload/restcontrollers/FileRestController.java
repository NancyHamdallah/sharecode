package com.example.editorwithpayload.restcontrollers;

import com.example.editorwithpayload.entity.File;
import com.example.editorwithpayload.service.FileService;
import com.example.editorwithpayload.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/file")
public class FileRestController {

    private FileService fileService;

    private UserService userService;
    public FileRestController(FileService fileService, UserService userService){
        this.fileService = fileService;
        this.userService = userService;

    }

    @GetMapping("/retrieve")
    public ResponseEntity<List<File>> getUserFiles(Model model){
        System.out.println("Files:"+fileService.findUserFiles());
        List<File> files = fileService.findUserFiles();;
        model.addAttribute("files",files);
        return ResponseEntity.ok(files);
    }
}
