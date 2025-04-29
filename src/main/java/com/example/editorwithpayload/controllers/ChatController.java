package com.example.editorwithpayload.controllers;


import com.example.editorwithpayload.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/sendMessage/**")
    public ChatMessage sendMessage(ChatMessage message){
        messagingTemplate.convertAndSend("/topic/room/" + message.getRoom(), message);
        return message;
    }

}
