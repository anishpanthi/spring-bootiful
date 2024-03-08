package dev.app.demo.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anish Panthi
 */
@RestController
public class UnsecuredController {

  @GetMapping("/unsecured/api")
  public Map<String, String> unsecuredApi() {
    return Map.of("message", "This is an unsecured API");
  }
}
