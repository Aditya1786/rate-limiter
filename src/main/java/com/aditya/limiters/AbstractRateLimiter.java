package com.aditya.limiters;

import com.aditya.interfaces.RateLimiter;

public abstract class AbstractRateLimiter implements RateLimiter {
  protected int maxRequests;
  protected long windowSizeInMillis;

  public AbstractRateLimiter(int maxRequests, long windowSizeInMillis) {
    this.maxRequests = maxRequests;
    this.windowSizeInMillis = windowSizeInMillis;
  }

  @Override
  public boolean allowRequest(String userId) {
    return isRequestAllowed(userId);
  }

  protected abstract boolean isRequestAllowed(String userId);
}
