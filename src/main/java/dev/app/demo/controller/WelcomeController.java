package dev.app.demo.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

  @GetMapping
  public Map<String, String> welcome() {
    return Map.of("message", "Welcome to the live coding session!");
  }
}
