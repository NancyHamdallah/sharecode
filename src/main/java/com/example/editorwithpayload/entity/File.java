package com.example.editorwithpayload.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "files")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class File{

    @Id
    private String id;
    private String createdBy;

}