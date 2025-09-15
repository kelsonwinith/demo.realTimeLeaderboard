package com.example.backend.modules.greeting;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public GreetingResponse greeting(GreetingRequest message) throws Exception {
    Thread.sleep(1000); // simulated delay
    return new GreetingResponse("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
  }

}