package com.muyie.redis.cache;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * RedissonCache
 *
 * @author larry.qi
 * @since 2.7.13
 **/
@Slf4j
public class RedissonCache {

  private final static String MSG_NOT_ACQUIRED = "The lock was not acquired, lockName=";
  private final static String MSG_FAILED_ACQUIRED = "Failed to acquire lock, lockName=";

  private final RedissonClient redissonClient;

  public RedissonCache(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  /**
   * 获取 RedissonClient 对象，使用原生接口能力
   *
   * @return RedissonClient 对象
   */
  public RedissonClient getRedisson() {
    return Objects.requireNonNull(redissonClient);
  }

  /**
   * 获取并发锁 - 无等待时间 - 如果没有显式调用 unlock() 方法，默认 30s 后会自动释放锁
   *
   * @param name        锁的名称
   * @param runnable    业务处理
   * @param forceUnlock 是否强制释放锁（true表示显式调用 unlock() 方法）
   */
  public void lock(String name, Runnable runnable, boolean forceUnlock) {
    RLock rLock = redissonClient.getLock(name);
    try {
      rLock.lock();
      runnable.run();
    } finally {
      if (forceUnlock) {
        rLock.forceUnlock();
      }
    }
  }

  /**
   * 获取并发锁 - 无等待时间 - 如果没有显式调用 unlock() 方法，超时时间后会自动释放锁
   *
   * @param name        锁的名称
   * @param leaseTime   超时时间
   * @param timeUnit    时间单位
   * @param runnable    业务处理
   * @param forceUnlock 是否强制释放锁（true表示显式调用 unlock() 方法）
   */
  public void lock(String name, long leaseTime, TimeUnit timeUnit, Runnable runnable, boolean forceUnlock) {
    RLock rLock = redissonClient.getLock(name);
    try {
      rLock.lock(leaseTime, timeUnit);
      runnable.run();
    } finally {
      if (forceUnlock) {
        rLock.forceUnlock();
      }
    }
  }

  /**
   * 尝试获取并发锁 - 无等待时间 - 如果没有显式调用 unlock() 方法，默认 30s 后会自动释放锁
   *
   * @param name        锁的名称
   * @param runnable    业务处理
   * @param forceUnlock 是否强制释放锁（true表示显式调用 unlock() 方法）
   * @return true=加锁处理成功，false=未获取到锁
   */
  public boolean tryLock(String name, Runnable runnable, boolean forceUnlock) {
    RLock rLock = redissonClient.getLock(name);
    boolean tryLock = rLock.tryLock();
    if (tryLock) {
      try {
        runnable.run();
      } finally {
        if (forceUnlock) {
          rLock.forceUnlock();
        }
      }
    } else {
      log.warn(MSG_NOT_ACQUIRED + name);
    }
    return tryLock;
  }

  /**
   * 尝试获取并发锁 - 如果没有显式调用 unlock() 方法，默认 30s 后会自动释放锁
   *
   * @param name        锁的名称
   * @param waitTime    等待时间
   * @param timeUnit    时间单位
   * @param runnable    业务处理
   * @param forceUnlock 是否强制释放锁（true表示显式调用 unlock() 方法）
   * @return true=加锁处理成功，false=未获取到锁
   */
  public boolean tryLock(String name, long waitTime, TimeUnit timeUnit, Runnable runnable, boolean forceUnlock) {
    RLock rLock = redissonClient.getLock(name);
    try {
      boolean tryLock = rLock.tryLock(waitTime, timeUnit);
      if (tryLock) {
        try {
          runnable.run();
        } finally {
          if (forceUnlock) {
            rLock.forceUnlock();
          }
        }
      } else {
        log.warn(MSG_NOT_ACQUIRED + name);
      }
      return tryLock;
    } catch (Exception e) {
      log.error(MSG_FAILED_ACQUIRED + name, e);
    }
    return false;
  }

  /**
   * 尝试获取并发锁 - 如果没有显式调用 unlock() 方法，超时时间后会自动释放锁
   *
   * @param name        锁的名称
   * @param waitTime    等待时间
   * @param leaseTime   超时时间
   * @param timeUnit    时间单位
   * @param runnable    业务处理
   * @param forceUnlock 是否强制释放锁（true表示显式调用 unlock() 方法）
   * @return true=加锁处理成功，false=未获取到锁
   */
  public boolean tryLock(String name, long waitTime, long leaseTime, TimeUnit timeUnit, Runnable runnable, boolean forceUnlock) {
    RLock rLock = redissonClient.getLock(name);
    try {
      boolean tryLock = rLock.tryLock(waitTime, leaseTime, timeUnit);
      if (tryLock) {
        try {
          runnable.run();
        } finally {
          if (forceUnlock) {
            rLock.forceUnlock();
          }
        }
      } else {
        log.warn(MSG_NOT_ACQUIRED + name);
      }
      return tryLock;
    } catch (Exception e) {
      log.error(MSG_FAILED_ACQUIRED + name, e);
    }
    return false;
  }

  /**
   * 分布式系统的定时任务处理，防止并发。锁的持续时间默认为 30s
   *
   * @param name     锁的名称
   * @param runnable 业务处理
   * @return true=加锁处理成功，false=未获取到锁
   */
  public boolean tryScheduledTask(String name, Runnable runnable) {
    return this.tryLock(name, runnable, false);
  }

  /**
   * 分布式系统的定时任务处理，防止并发。锁的持续时间为超时时间
   *
   * @param name      锁的名称
   * @param leaseTime 超时时间
   * @param timeUnit  时间单位
   * @param runnable  业务处理
   * @return true=加锁处理成功，false=未获取到锁
   */
  public boolean tryScheduledTask(String name, long leaseTime, TimeUnit timeUnit, Runnable runnable) {
    return this.tryLock(name, -1, leaseTime, timeUnit, runnable, false);
  }

}
