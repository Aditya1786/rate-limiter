package com.aditya.factory;

import com.aditya.interfaces.RateLimiter;
import com.aditya.limiters.FixedWindowRateLimiter;
import com.aditya.limiters.SlidingWindowRateLimiter;

public class RateLimiterFactory {
  public static RateLimiter createRateLimiter(
      String type, int maxRequests, long windowSizeInMillis) {
    return switch (type.toLowerCase()) {
      case "fixed_window" -> new FixedWindowRateLimiter(maxRequests, windowSizeInMillis);
      case "sliding_window" -> new SlidingWindowRateLimiter(maxRequests, windowSizeInMillis);
      default -> throw new IllegalArgumentException("Unknown Rate Limiter type: " + type);
    };
  }
}
