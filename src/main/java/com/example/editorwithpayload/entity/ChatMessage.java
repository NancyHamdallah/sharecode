package com.example.editorwithpayload.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatMessage {
    private Long Id;
    private String senderId;
    private String content;
    private String room;
}
