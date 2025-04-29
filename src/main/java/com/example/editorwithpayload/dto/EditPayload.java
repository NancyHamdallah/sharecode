package com.example.editorwithpayload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditPayload {
    private String userId;
    private String userName;
    private String roomId;
    private String fileContent;
}
