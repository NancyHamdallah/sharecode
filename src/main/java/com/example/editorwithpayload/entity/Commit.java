package com.example.editorwithpayload.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "commits")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commit{
    @Id
    private String commitId;
    private String commitMsg;
    private int version;
    private String urlLink;
    @DBRef(lazy = false)
    private User userId;
    @DBRef(lazy = false)
    private File fileId;


}