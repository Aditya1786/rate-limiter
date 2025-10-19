package com.aditya.interfaces;

public interface RateLimiter {
  boolean allowRequest(String userId);
}
