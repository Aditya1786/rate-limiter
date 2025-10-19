package com.aditya.manager;

import com.aditya.factory.RateLimiterFactory;
import com.aditya.interfaces.RateLimiter;

public class RateLimiterManager {
  private static volatile RateLimiterManager instance;
  private final RateLimiter rateLimiter;

  private RateLimiterManager(String type, int maxRequests, long windowSizeInMillis) {
    this.rateLimiter = RateLimiterFactory.createRateLimiter(type, maxRequests, windowSizeInMillis);
  }

  public static RateLimiterManager getInstance(
      String type, int maxRequests, long windowSizeInMillis) {
    // Double-checked Locking
    // Thread Safe
    if (instance == null) {
      synchronized (RateLimiterManager.class) {
        if (instance == null) {
          instance = new RateLimiterManager(type, maxRequests, windowSizeInMillis);
        }
      }
    }
    return instance;
  }

  public boolean allowRequest(String userId) {
    return rateLimiter.allowRequest(userId);
  }
}
