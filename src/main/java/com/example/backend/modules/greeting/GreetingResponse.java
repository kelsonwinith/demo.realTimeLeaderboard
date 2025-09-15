package com.example.backend.modules.greeting;

public class GreetingResponse {

    private String content;

    public GreetingResponse() {
    }

    public GreetingResponse(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}