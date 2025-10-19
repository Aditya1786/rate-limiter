package com.aditya;

import com.aditya.manager.RateLimiterManager;

public class Main {
  public static void main(String[] args) {
    //    RateLimiterManager rateLimiterManager = RateLimiterManager.getInstance("fixed_window", 10,
    // 60000);
    RateLimiterManager rateLimiterManager =
        RateLimiterManager.getInstance("sliding_window", 10, 60000);

    System.out.println("Fixed Window Rate Limiter:");
    for (int i = 0; i < 12; i++) {
      System.out.println(rateLimiterManager.allowRequest("user1"));
    }

    System.out.println("Sliding Window Rate Limiter:");
    for (int i = 0; i < 15; i++) {
      System.out.println(rateLimiterManager.allowRequest("user1"));
    }
  }
}
