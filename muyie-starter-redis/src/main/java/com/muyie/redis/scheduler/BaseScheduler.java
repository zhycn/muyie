package com.muyie.redis.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StopWatch;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class BaseScheduler {

  private static final String PREFIX = "scheduler:";

  @Autowired
  private RedissonClient redissonClient;

  /**
   * 任务名称
   *
   * @param taskName 任务名称
   * @param runnable 执行内容
   */
  public void doTask(String taskName, Runnable runnable) {
    try {
      // 生成一个缓存key
      String name = PREFIX + DigestUtils.md5DigestAsHex(taskName.getBytes(Charset.defaultCharset()));
      RLock lock = redissonClient.getLock(name);
      // 尝试获取锁，锁的有效期为60秒
      if (lock.tryLock(0, 60, TimeUnit.SECONDS)) {
        log.info("[{}]任务开始执行", taskName);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        runnable.run();
        stopWatch.stop();
        log.info("[{}]任务执行结束：{}", taskName, "': running time = "
          + stopWatch.getTotalTimeMillis() + " ms");
      } else {
        log.info("[{}]任务获取并发锁失败", taskName);
      }
    } catch (Exception e) {
      log.error("[{}]任务开始执行失败: {}", taskName, e.getMessage());
    }
  }

}
