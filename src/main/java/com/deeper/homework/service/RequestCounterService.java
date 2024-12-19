package com.deeper.homework.service;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class RequestCounterService {
  private final AtomicInteger activeRequests = new AtomicInteger(0);

  public void increment() {
    activeRequests.incrementAndGet();
  }

  public void decrement() {
    activeRequests.decrementAndGet();
  }

  public int getActiveRequests() {
    return activeRequests.get();
  }
}