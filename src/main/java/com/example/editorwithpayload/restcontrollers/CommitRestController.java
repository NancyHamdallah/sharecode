package com.example.editorwithpayload.restcontrollers;

import com.example.editorwithpayload.dto.CommitDTO;
import com.example.editorwithpayload.entity.Commit;
import com.example.editorwithpayload.service.CommitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class CommitRestController {

    private CommitService commitService;

    public CommitRestController(CommitService commitService){
        this.commitService = commitService;
    }


    @GetMapping("/commits/by-file/{fileId}")
    public ResponseEntity<List<Commit>> getCommits(@PathVariable String fileId) {
        try {
            List<Commit> fileCommits = commitService.findAllByFileId(fileId);
            System.out.println("fileCommits:"+fileCommits.toString());
            return ResponseEntity.ok(fileCommits);
        } catch (Exception e) {
            // Log the error (good practice)
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
