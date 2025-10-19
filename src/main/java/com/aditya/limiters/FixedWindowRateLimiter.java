package com.aditya.limiters;

import com.aditya.interfaces.RateLimiter;
import java.util.HashMap;
import java.util.Map;

public class FixedWindowRateLimiter implements RateLimiter {
  private final int maxRequests;
  private final long windowSizeInMillis;
  private final Map<String, Integer> requestCounts;
  private final Map<String, Long> windowStartTimes;

  public FixedWindowRateLimiter(int maxRequests, long windowSizeInMillis) {
    this.maxRequests = maxRequests;
    this.windowSizeInMillis = windowSizeInMillis;
    this.requestCounts = new HashMap<>();
    this.windowStartTimes = new HashMap<>();
  }

  @Override
  public boolean allowRequest(String userId) {
    long currentTime = System.currentTimeMillis();
    long windowStartTime = windowStartTimes.getOrDefault(userId, 0L);

    if (currentTime - windowStartTime >= windowSizeInMillis) {
      // Reset the window
      windowStartTimes.put(userId, currentTime);
      requestCounts.put(userId, 1);
      return true;
    } else {
      int currentCount = requestCounts.getOrDefault(userId, 0);
      if (currentCount < maxRequests) {
        requestCounts.put(userId, currentCount + 1);
        return true;
      } else {
        return false;
      }
    }
  }
}
