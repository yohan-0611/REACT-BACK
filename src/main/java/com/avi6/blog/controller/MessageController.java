package com.avi6.blog.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avi6.blog.model.Message;
import com.avi6.blog.repository.MessageRepository;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.0.32:3000"}) 
public class MessageController {

    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/messages")
    public List<Message> getMessages() {
    	System.out.println("ㅔㅈ발 되라");
        return messageRepository.findAll();
    }
}
