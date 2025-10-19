package com.aditya.template;

import com.aditya.limiters.AbstractRateLimiter;
import java.util.HashMap;
import java.util.Map;

public class FixedWindowRateLimiterTemplate extends AbstractRateLimiter {
  private final Map<String, Integer> requestCounts;
  private final Map<String, Long> windowStartTimes;

  public FixedWindowRateLimiterTemplate(int maxRequests, long windowSizeInMillis) {
    super(maxRequests, windowSizeInMillis);
    windowStartTimes = new HashMap<>();
    requestCounts = new HashMap<>();
  }

  @Override
  protected boolean isRequestAllowed(String userId) {
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
