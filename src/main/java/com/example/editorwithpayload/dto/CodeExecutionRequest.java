package com.example.editorwithpayload.dto;

import lombok.Data;

@Data
public class CodeExecutionRequest {
    private String code;
    private String language;
}
