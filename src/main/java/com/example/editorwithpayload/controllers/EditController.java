package com.example.editorwithpayload.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class EditController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final ConcurrentHashMap<String, String> roomFileMap = new ConcurrentHashMap<>();

    public EditController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    @MessageMapping("/edit/**") // wildcard for all /app/edit/xxx
    public void handleEdit(Message<byte[]> message) throws Exception {
        String destination = (String) message.getHeaders().get(SimpMessageHeaderAccessor.DESTINATION_HEADER);

        // Extract roomId from destination
        String roomId = destination.substring(destination.lastIndexOf("/") + 1);

        String content = new String(message.getPayload(), StandardCharsets.UTF_8);
        System.out.println("FileID: "+ roomId);
        // Broadcast to same file's topic
        messagingTemplate.convertAndSend("/topic/edit/" + roomId, content);


    }

}