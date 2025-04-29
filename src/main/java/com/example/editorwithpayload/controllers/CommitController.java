package com.example.editorwithpayload.controllers;

import com.example.editorwithpayload.dto.CommitDTO;
import com.example.editorwithpayload.entity.Commit;
import com.example.editorwithpayload.service.CommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class CommitController {

    @Autowired
    private CommitService commitService;

    public CommitController(CommitService commitService){
        this.commitService = commitService;
    }

    @PostMapping("/commit")
    public String commitChange(@RequestBody CommitDTO commitDTO, Model model) {
        if (commitDTO.getCommitMsg() == null || commitDTO.getCommitMsg().trim().isEmpty()) {
            model.addAttribute("error", "Commit message is required.");
            return "editor";
        }
        System.out.println("Room ID: " + commitDTO.getRoomId());
        System.out.println("Username: " + commitDTO.getUsername());
        System.out.println("Language: " + commitDTO.getLanguage());
        System.out.println("Code Content: " + commitDTO.getContent());
        System.out.println("Commit Message: " + commitDTO.getCommitMsg());
        Commit success = commitService.createCommit(commitDTO);
        if (success != null) {
            model.addAttribute("success", "Changes committed successfully!");
        } else {
            model.addAttribute("error", "Failed to commit changes.");
        }

        return "editor";
    }




}
