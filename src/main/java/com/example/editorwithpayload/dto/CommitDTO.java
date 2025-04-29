package com.example.editorwithpayload.dto;

import com.example.editorwithpayload.entity.File;
import com.example.editorwithpayload.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitDTO {



    private String roomId;
    private String username;
    private String language;
    private String content;
    private String commitMsg;


}
