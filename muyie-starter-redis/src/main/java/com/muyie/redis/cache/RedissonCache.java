package com.muyie.redis.cache;

import org.redisson.api.BatchOptions;
import org.redisson.api.ExecutorOptions;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.LockOptions;
import org.redisson.api.MapOptions;
import org.redisson.api.RAtomicDouble;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBatch;
import org.redisson.api.RBinaryStream;
import org.redisson.api.RBitSet;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RBoundedBlockingQueue;
import org.redisson.api.RBucket;
import org.redisson.api.RBuckets;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RDeque;
import org.redisson.api.RDoubleAdder;
import org.redisson.api.RGeo;
import org.redisson.api.RHyperLogLog;
import org.redisson.api.RIdGenerator;
import org.redisson.api.RKeys;
import org.redisson.api.RLexSortedSet;
import org.redisson.api.RList;
import org.redisson.api.RListMultimap;
import org.redisson.api.RListMultimapCache;
import org.redisson.api.RLiveObjectService;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RLock;
import org.redisson.api.RLongAdder;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RPatternTopic;
import org.redisson.api.RPermitExpirableSemaphore;
import org.redisson.api.RPriorityBlockingDeque;
import org.redisson.api.RPriorityBlockingQueue;
import org.redisson.api.RPriorityDeque;
import org.redisson.api.RPriorityQueue;
import org.redisson.api.RQueue;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RReliableTopic;
import org.redisson.api.RRemoteService;
import org.redisson.api.RRingBuffer;
import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RScript;
import org.redisson.api.RSemaphore;
import org.redisson.api.RSet;
import org.redisson.api.RSetCache;
import org.redisson.api.RSetMultimap;
import org.redisson.api.RSetMultimapCache;
import org.redisson.api.RSortedSet;
import org.redisson.api.RStream;
import org.redisson.api.RTimeSeries;
import org.redisson.api.RTopic;
import org.redisson.api.RTransaction;
import org.redisson.api.RTransferQueue;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.api.TransactionOptions;
import org.redisson.api.redisnode.BaseRedisNodes;
import org.redisson.api.redisnode.RedisNodes;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * RedissonCache
 *
 * @author larry.qi
 * @since 1.2.5
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
   * Returns Redisson Client instance
   *
   * @return redisson client instance
   */
  public RedissonClient redisson() {
    return redissonClient;
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

  /**
   * Returns time-series instance by <code>name</code>
   *
   * @param <V>  type of value
   * @param <L>  type of value
   * @param name - name of instance
   * @return RTimeSeries object
   */
  public <V, L> RTimeSeries<V, L> getTimeSeries(String name) {
    return redissonClient.getTimeSeries(name);
  }

  /**
   * Returns stream instance by <code>name</code>
   * <p>
   * Requires <b>Redis 5.0.0 and higher.</b>
   *
   * @param <K>  type of key
   * @param <V>  type of value
   * @param name of stream
   * @return RStream object
   */
  public <K, V> RStream<K, V> getStream(String name) {
    return redissonClient.getStream(name);
  }

  /**
   * Returns rate limiter instance by <code>name</code>
   *
   * @param name of rate limiter
   * @return RateLimiter object
   */
  public RRateLimiter getRateLimiter(String name) {
    return redissonClient.getRateLimiter(name);
  }

  /**
   * Returns binary stream holder instance by <code>name</code>
   *
   * @param name of binary stream
   * @return BinaryStream object
   */
  public RBinaryStream getBinaryStream(String name) {
    return redissonClient.getBinaryStream(name);
  }

  /**
   * Returns geospatial items holder instance by <code>name</code>.
   *
   * @param <V>  type of value
   * @param name - name of object
   * @return Geo object
   */
  public <V> RGeo<V> getGeo(String name) {
    return redissonClient.getGeo(name);
  }

  /**
   * Returns set-based cache instance by <code>name</code>. Supports value eviction with a given TTL
   * value.
   *
   * <p>If eviction is not required then it's better to use regular map.</p>
   *
   * @param <V>  type of value
   * @param name - name of object
   * @return SetCache object
   */
  public <V> RSetCache<V> getSetCache(String name) {
    return redissonClient.getSetCache(name);
  }

  /**
   * Returns map-based cache instance by name. Supports entry eviction with a given MaxIdleTime and
   * TTL settings.
   * <p>
   * If eviction is not required then it's better to use regular map {@link #getMap(String)}.</p>
   *
   * @param <K>  type of key
   * @param <V>  type of value
   * @param name - name of object
   * @return MapCache object
   */
  public <K, V> RMapCache<K, V> getMapCache(String name) {
    return redissonClient.getMapCache(name);
  }

  /**
   * Returns map-based cache instance by name. Supports entry eviction with a given MaxIdleTime and
   * TTL settings.
   * <p>
   * If eviction is not required then it's better to use regular map {@link #getMap(String)}.</p>
   *
   * @param <K>     type of key
   * @param <V>     type of value
   * @param name    - name of object
   * @param options - map options
   * @return MapCache object
   */
  public <K, V> RMapCache<K, V> getMapCache(String name, MapOptions<K, V> options) {
    return redissonClient.getMapCache(name, options);
  }

  /**
   * Returns object holder instance by name.
   *
   * @param <V>  type of value
   * @param name - name of object
   * @return Bucket object
   */
  public <V> RBucket<V> getBucket(String name) {
    return redissonClient.getBucket(name);
  }

  /**
   * Returns interface for mass operations with Bucket objects.
   *
   * @return Buckets
   */
  public RBuckets getBuckets() {
    return redissonClient.getBuckets();
  }

  /**
   * Returns HyperLogLog instance by name.
   *
   * @param <V>  type of value
   * @param name - name of object
   * @return HyperLogLog object
   */
  public <V> RHyperLogLog<V> getHyperLogLog(String name) {
    return redissonClient.getHyperLogLog(name);
  }

  /**
   * Returns list instance by name.
   *
   * @param <V>  type of value
   * @param name - name of object
   * @return List object
   */
  public <V> RList<V> getList(String name) {
    return redissonClient.getList(name);
  }

  /**
   * Returns List based Multimap instance by name.
   *
   * @param <K>  type of key
   * @param <V>  type of value
   * @param name - name of object
   * @return ListMultimap object
   */
  public <K, V> RListMultimap<K, V> getListMultimap(String name) {
    return redissonClient.getListMultimap(name);
  }

  /**
   * Returns List based Multimap instance by name. Supports key-entry eviction with a given TTL
   * value.
   *
   * <p>If eviction is not required then it's better to use regular map
   * {@link #getSetMultimap(String)}.</p>
   *
   * @param <K>  type of key
   * @param <V>  type of value
   * @param name - name of object
   * @return ListMultimapCache object
   */
  public <K, V> RListMultimapCache<K, V> getListMultimapCache(String name) {
    return redissonClient.getListMultimapCache(name);
  }

  /**
   * Returns local cached map instance by name. Configured by parameters of options-object.
   *
   * @param <K>     type of key
   * @param <V>     type of value
   * @param name    - name of object
   * @param options - local map options
   * @return LocalCachedMap object
   */
  public <K, V> RLocalCachedMap<K, V> getLocalCachedMap(String name, LocalCachedMapOptions<K, V> options) {
    return redissonClient.getLocalCachedMap(name, options);
  }

  /**
   * Returns map instance by name.
   *
   * @param <K>  type of key
   * @param <V>  type of value
   * @param name - name of object
   * @return Map object
   */
  public <K, V> RMap<K, V> getMap(String name) {
    return redissonClient.getMap(name);
  }

  /**
   * Returns map instance by name.
   *
   * @param <K>     type of key
   * @param <V>     type of value
   * @param name    - name of object
   * @param options - map options
   * @return Map object
   */
  public <K, V> RMap<K, V> getMap(String name, MapOptions<K, V> options) {
    return redissonClient.getMap(name, options);
  }

  /**
   * Returns Set based Multimap instance by name.
   *
   * @param <K>  type of key
   * @param <V>  type of value
   * @param name - name of object
   * @return SetMultimap object
   */
  public <K, V> RSetMultimap<K, V> getSetMultimap(String name) {
    return redissonClient.getSetMultimap(name);
  }

  /**
   * Returns Set based Multimap instance by name. Supports key-entry eviction with a given TTL
   * value.
   *
   * <p>If eviction is not required then it's better to use regular map
   * {@link #getSetMultimap(String)}.</p>
   *
   * @param <K>  type of key
   * @param <V>  type of value
   * @param name - name of object
   * @return SetMultimapCache object
   */
  public <K, V> RSetMultimapCache<K, V> getSetMultimapCache(String name) {
    return redissonClient.getSetMultimapCache(name);
  }

  /**
   * Returns semaphore instance by name
   *
   * @param name - name of object
   * @return Semaphore object
   */
  public RSemaphore getSemaphore(String name) {
    return redissonClient.getSemaphore(name);
  }

  /**
   * Returns semaphore instance by name. Supports lease time parameter for each acquired permit.
   *
   * @param name - name of object
   * @return PermitExpirableSemaphore object
   */
  public RPermitExpirableSemaphore getPermitExpirableSemaphore(String name) {
    return redissonClient.getPermitExpirableSemaphore(name);
  }

  /**
   * Returns Lock instance by name.
   * <p>
   * Implements a <b>non-fair</b> locking so doesn't guarantees an acquire order by threads.
   * <p>
   * To increase reliability during failover, all operations wait for propagation to all Redis
   * slaves.
   *
   * @param name - name of object
   * @return Lock object
   */
  public RLock getLock(String name) {
    return redissonClient.getLock(name);
  }

  /**
   * Returns Spin lock instance by name.
   * <p>
   * Implements a <b>non-fair</b> locking so doesn't guarantees an acquire order by threads.
   * <p>
   * Lock doesn't use a pub/sub mechanism
   *
   * @param name - name of object
   * @return Lock object
   */
  public RLock getSpinLock(String name) {
    return redissonClient.getSpinLock(name);
  }

  /**
   * Returns Spin lock instance by name with specified back off options.
   * <p>
   * Implements a <b>non-fair</b> locking so doesn't guarantees an acquire order by threads.
   * <p>
   * Lock doesn't use a pub/sub mechanism
   *
   * @param name    - name of object
   * @param backOff LockOptions.BackOff
   * @return Lock object
   */
  public RLock getSpinLock(String name, LockOptions.BackOff backOff) {
    return redissonClient.getSpinLock(name, backOff);
  }

  /**
   * Returns MultiLock instance associated with specified <code>locks</code>
   *
   * @param locks - collection of locks
   * @return MultiLock object
   */
  public RLock getMultiLock(RLock... locks) {
    return redissonClient.getMultiLock(locks);
  }

  /**
   * Returns Lock instance by name.
   * <p>
   * Implements a <b>fair</b> locking so it guarantees an acquire order by threads.
   * <p>
   * To increase reliability during failover, all operations wait for propagation to all Redis
   * slaves.
   *
   * @param name - name of object
   * @return Lock object
   */
  public RLock getFairLock(String name) {
    return redissonClient.getFairLock(name);
  }

  /**
   * Returns ReadWriteLock instance by name.
   * <p>
   * To increase reliability during failover, all operations wait for propagation to all Redis
   * slaves.
   *
   * @param name - name of object
   * @return Lock object
   */
  public RReadWriteLock getReadWriteLock(String name) {
    return redissonClient.getReadWriteLock(name);
  }

  /**
   * Returns set instance by name.
   *
   * @param <V>  type of value
   * @param name - name of object
   * @return Set object
   */
  public <V> RSet<V> getSet(String name) {
    return redissonClient.getSet(name);
  }

  /**
   * Returns sorted set instance by name. This sorted set uses comparator to sort objects.
   *
   * @param <V>  type of value
   * @param name - name of object
   * @return SortedSet object
   */
  public <V> RSortedSet<V> getSortedSet(String name) {
    return redissonClient.getSortedSet(name);
  }

  /**
   * Returns Redis Sorted Set instance by name. This sorted set sorts objects by object score.
   *
   * @param <V>  type of value
   * @param name - name of object
   * @return ScoredSortedSet object
   */
  public <V> RScoredSortedSet<V> getScoredSortedSet(String name) {
    return redissonClient.getScoredSortedSet(name);
  }

  /**
   * Returns String based Redis Sorted Set instance by name All elements are inserted with the same
   * score during addition, in order to force lexicographical ordering
   *
   * @param name - name of object
   * @return LexSortedSet object
   */
  public RLexSortedSet getLexSortedSet(String name) {
    return redissonClient.getLexSortedSet(name);
  }

  /**
   * Returns topic instance by name.
   * <p>
   * Messages are delivered to all listeners attached to the same Redis setup.
   * <p>
   *
   * @param name - name of object
   * @return Topic object
   */
  public RTopic getTopic(String name) {
    return redissonClient.getTopic(name);
  }

  /**
   * Returns reliable topic instance by name.
   * <p>
   * Dedicated Redis connection is allocated per instance (subscriber) of this object. Messages are
   * delivered to all listeners attached to the same Redis setup.
   * <p>
   * Requires <b>Redis 5.0.0 and higher.</b>
   *
   * @param name - name of object
   * @return ReliableTopic object
   */
  public RReliableTopic getReliableTopic(String name) {
    return redissonClient.getReliableTopic(name);
  }

  /**
   * Returns topic instance satisfies by pattern name.
   * <p>
   * Supported glob-style patterns: h?llo subscribes to hello, hallo and hxllo h*llo subscribes to
   * hllo and heeeello h[ae]llo subscribes to hello and hallo, but not hillo
   *
   * @param pattern of the topic
   * @return PatterTopic object
   */
  public RPatternTopic getPatternTopic(String pattern) {
    return redissonClient.getPatternTopic(pattern);
  }

  /**
   * Returns unbounded queue instance by name.
   *
   * @param <V>  type of value
   * @param name of object
   * @return queue object
   */
  public <V> RQueue<V> getQueue(String name) {
    return redissonClient.getQueue(name);
  }

  /**
   * Returns transfer queue instance by name.
   *
   * @param <V>  type of values
   * @param name - name of object
   * @return TransferQueue object
   */
  public <V> RTransferQueue<V> getTransferQueue(String name) {
    return redissonClient.getTransferQueue(name);
  }

  /**
   * Returns unbounded delayed queue instance by name.
   * <p>
   * Could be attached to destination queue only. All elements are inserted with transfer delay to
   * destination queue.
   *
   * @param <V>              type of value
   * @param destinationQueue - destination queue
   * @return Delayed queue object
   */
  public <V> RDelayedQueue<V> getDelayedQueue(RQueue<V> destinationQueue) {
    return redissonClient.getDelayedQueue(destinationQueue);
  }

  /**
   * Returns RingBuffer based queue.
   *
   * @param <V>  value type
   * @param name - name of object
   * @return RingBuffer object
   */
  public <V> RRingBuffer<V> getRingBuffer(String name) {
    return redissonClient.getRingBuffer(name);
  }

  /**
   * Returns priority unbounded queue instance by name. It uses comparator to sort objects.
   *
   * @param <V>  type of value
   * @param name of object
   * @return Queue object
   */
  public <V> RPriorityQueue<V> getPriorityQueue(String name) {
    return redissonClient.getPriorityQueue(name);
  }

  /**
   * Returns unbounded priority blocking queue instance by name. It uses comparator to sort
   * objects.
   *
   * @param <V>  type of value
   * @param name of object
   * @return Queue object
   */
  public <V> RPriorityBlockingQueue<V> getPriorityBlockingQueue(String name) {
    return redissonClient.getPriorityBlockingQueue(name);
  }

  /**
   * Returns unbounded priority blocking deque instance by name. It uses comparator to sort
   * objects.
   *
   * @param <V>  type of value
   * @param name of object
   * @return Queue object
   */
  public <V> RPriorityBlockingDeque<V> getPriorityBlockingDeque(String name) {
    return redissonClient.getPriorityBlockingDeque(name);
  }

  /**
   * Returns priority unbounded deque instance by name. It uses comparator to sort objects.
   *
   * @param <V>  type of value
   * @param name of object
   * @return Queue object
   */
  public <V> RPriorityDeque<V> getPriorityDeque(String name) {
    return redissonClient.getPriorityDeque(name);
  }

  /**
   * Returns unbounded blocking queue instance by name.
   *
   * @param <V>  type of value
   * @param name - name of object
   * @return BlockingQueue object
   */
  public <V> RBlockingQueue<V> getBlockingQueue(String name) {
    return redissonClient.getBlockingQueue(name);
  }

  /**
   * Returns bounded blocking queue instance by name.
   *
   * @param <V>  type of value
   * @param name of queue
   * @return BoundedBlockingQueue object
   */
  public <V> RBoundedBlockingQueue<V> getBoundedBlockingQueue(String name) {
    return redissonClient.getBoundedBlockingQueue(name);
  }

  /**
   * Returns unbounded deque instance by name.
   *
   * @param <V>  type of value
   * @param name - name of object
   * @return Deque object
   */
  public <V> RDeque<V> getDeque(String name) {
    return redissonClient.getDeque(name);
  }

  /**
   * Returns unbounded blocking deque instance by name.
   *
   * @param <V>  type of value
   * @param name - name of object
   * @return BlockingDeque object
   */
  public <V> RBlockingDeque<V> getBlockingDeque(String name) {
    return redissonClient.getBlockingDeque(name);
  }

  /**
   * Returns atomicLong instance by name.
   *
   * @param name - name of object
   * @return AtomicLong object
   */
  public RAtomicLong getAtomicLong(String name) {
    return redissonClient.getAtomicLong(name);
  }

  /**
   * Returns atomicDouble instance by name.
   *
   * @param name - name of object
   * @return AtomicDouble object
   */
  public RAtomicDouble getAtomicDouble(String name) {
    return redissonClient.getAtomicDouble(name);
  }

  /**
   * Returns LongAdder instances by name.
   *
   * @param name - name of object
   * @return LongAdder object
   */
  public RLongAdder getLongAdder(String name) {
    return redissonClient.getLongAdder(name);
  }

  /**
   * Returns DoubleAdder instances by name.
   *
   * @param name - name of object
   * @return LongAdder object
   */
  public RDoubleAdder getDoubleAdder(String name) {
    return redissonClient.getDoubleAdder(name);
  }

  /**
   * Returns countDownLatch instance by name.
   *
   * @param name - name of object
   * @return CountDownLatch object
   */
  public RCountDownLatch getCountDownLatch(String name) {
    return redissonClient.getCountDownLatch(name);
  }

  /**
   * Returns bitSet instance by name.
   *
   * @param name - name of object
   * @return BitSet object
   */
  public RBitSet getBitSet(String name) {
    return redissonClient.getBitSet(name);
  }

  /**
   * Returns bloom filter instance by name.
   *
   * @param <V>  type of value
   * @param name - name of object
   * @return BloomFilter object
   */
  public <V> RBloomFilter<V> getBloomFilter(String name) {
    return redissonClient.getBloomFilter(name);
  }

  /**
   * Returns id generator by name.
   *
   * @param name - name of object
   * @return IdGenerator object
   */
  public RIdGenerator getIdGenerator(String name) {
    return redissonClient.getIdGenerator(name);
  }

  /**
   * Returns script operations object
   *
   * @return Script object
   */
  public RScript getScript() {
    return redissonClient.getScript();
  }

  /**
   * Returns ScheduledExecutorService by name
   *
   * @param name - name of object
   * @return ScheduledExecutorService object
   */
  public RScheduledExecutorService getExecutorService(String name) {
    return redissonClient.getExecutorService(name);
  }

  /**
   * Returns ScheduledExecutorService by name
   *
   * @param name    - name of object
   * @param options - options for executor
   * @return ScheduledExecutorService object
   */
  public RScheduledExecutorService getExecutorService(String name, ExecutorOptions options) {
    return redissonClient.getExecutorService(name, options);
  }

  /**
   * Returns object for remote operations prefixed with the default name (redisson_remote_service)
   *
   * @return RemoteService object
   */
  public RRemoteService getRemoteService() {
    return redissonClient.getRemoteService();
  }

  /**
   * Returns object for remote operations prefixed with the specified name
   *
   * @param name - the name used as the Redis key prefix for the services
   * @return RemoteService object
   */
  public RRemoteService getRemoteService(String name) {
    return redissonClient.getRemoteService(name);
  }

  /**
   * Creates transaction with <b>READ_COMMITTED</b> isolation level.
   *
   * @param options - transaction configuration
   * @return Transaction object
   */
  public RTransaction createTransaction(TransactionOptions options) {
    return redissonClient.createTransaction(options);
  }

  /**
   * Creates batch object which could be executed later with collected group of commands in pipeline
   * mode.
   * <p>
   * See <a href="http://redis.io/topics/pipelining">http://redis.io/topics/pipelining</a>
   *
   * @param options - batch configuration
   * @return Batch object
   */
  public RBatch createBatch(BatchOptions options) {
    return redissonClient.createBatch(options);
  }

  /**
   * Creates batch object which could be executed later with collected group of commands in pipeline
   * mode.
   * <p>
   * See <a href="http://redis.io/topics/pipelining">http://redis.io/topics/pipelining</a>
   *
   * @return Batch object
   */
  public RBatch createBatch() {
    return redissonClient.createBatch();
  }

  /**
   * Returns interface with methods for Redis keys. Each of Redis/Redisson object associated with
   * own key
   *
   * @return Keys object
   */
  public RKeys getKeys() {
    return redissonClient.getKeys();
  }

  /**
   * Returns RedissonAttachedLiveObjectService which can be used to retrieve live REntity(s)
   *
   * @return LiveObjectService object
   */
  public RLiveObjectService getLiveObjectService() {
    return redissonClient.getLiveObjectService();
  }

  /**
   * Returns RxJava Redisson instance
   *
   * @return redisson instance
   */
  public RedissonRxClient rxJava() {
    return redissonClient.rxJava();
  }

  /**
   * Returns Reactive Redisson instance
   *
   * @return redisson instance
   */
  public RedissonReactiveClient reactive() {
    return redissonClient.reactive();
  }

  /**
   * Returns API to manage Redis nodes
   *
   * @param nodes Redis nodes API class
   * @param <T>   type of Redis nodes API
   * @return Redis nodes API object
   * @see RedisNodes#CLUSTER
   * @see RedisNodes#MASTER_SLAVE
   * @see RedisNodes#SENTINEL_MASTER_SLAVE
   * @see RedisNodes#SINGLE
   */
  public <T extends BaseRedisNodes> T getRedisNodes(RedisNodes<T> nodes) {
    return redissonClient.getRedisNodes(nodes);
  }

}
