package com.aditya.template;

import com.aditya.limiters.AbstractRateLimiter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class SlidingWindowRateLimiterTemplate extends AbstractRateLimiter {
  private final Map<String, Queue<Long>> requestTimestamps;

  public SlidingWindowRateLimiterTemplate(int maxRequests, long windowSizeInMillis) {
    super(maxRequests, windowSizeInMillis);
    this.requestTimestamps = new HashMap<>();
  }

  @Override
  protected boolean isRequestAllowed(String userId) {
    long currentTime = System.currentTimeMillis();
    requestTimestamps.putIfAbsent(userId, new LinkedList<>());

    // remove requests which differ by more than window size
    Queue<Long> timestamps = requestTimestamps.get(userId);
    while (!timestamps.isEmpty() && currentTime - timestamps.peek() > windowSizeInMillis) {
      timestamps.poll();
    }

    // check if we can allow the request
    if (timestamps.size() < maxRequests) {
      timestamps.offer(currentTime);
      return true;
    } else {
      return false;
    }
  }
}
