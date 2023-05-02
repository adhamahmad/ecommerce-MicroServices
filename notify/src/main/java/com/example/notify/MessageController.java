package com.example.notify;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MessageController {
    private String message;

    @GetMapping("/notifications")
    @ResponseBody
    public String showMessage(@RequestParam String email) {
        String[] parts = message.split(":");
        String messageEmail = parts[1].trim();
        if (messageEmail.equals(email)) {
            return message;
        } else {
            return "no notifications";
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }
}