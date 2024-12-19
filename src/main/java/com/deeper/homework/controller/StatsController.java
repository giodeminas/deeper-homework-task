package com.deeper.homework.controller;

import com.deeper.homework.service.RequestCounterService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class StatsController {

  private final RequestCounterService requestCounterService;

  @Autowired
  public StatsController(RequestCounterService requestCounterService) {
    this.requestCounterService = requestCounterService;
  }

  @GetMapping("/stats")
  public ResponseEntity<Map<String, Object>> getStats() {
    Map<String, Object> stats = new HashMap<>();
    stats.put("activeRequests", requestCounterService.getActiveRequests());
    return ResponseEntity.ok(stats);
  }
}
