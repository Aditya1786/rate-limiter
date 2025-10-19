package com.aditya.limiters;

import com.aditya.interfaces.RateLimiter;
import java.util.*;

public class SlidingWindowRateLimiter implements RateLimiter {
  private final int maxRequests;
  private final long windowSizeInMillis;
  private final Map<String, Queue<Long>> requestTimestamps;

  public SlidingWindowRateLimiter(int maxRequests, long windowSizeInMillis) {
    this.maxRequests = maxRequests;
    this.windowSizeInMillis = windowSizeInMillis;
    this.requestTimestamps = new HashMap<>();
  }

  @Override
  public boolean allowRequest(String userId) {
    long currentTime = System.currentTimeMillis();
    requestTimestamps.putIfAbsent(userId, new LinkedList<>());

    Queue<Long> timestamps = requestTimestamps.get(userId);

    // remove requests which differ by more than window size
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
