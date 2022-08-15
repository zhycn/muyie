package com.muyie.redis.cache;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.BoundGeoOperations;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundStreamOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Spring Data Redis 工具类
 *
 * @author larry.qi
 * @since 1.2.5
 **/
@Component
public class RedisCache {

  private final RedisTemplate redisTemplate;

  public RedisCache(RedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  /**
   * 获取 RedisTemplate 对象
   *
   * @return RedisTemplate 对象
   */
  public RedisTemplate getRedisTemplate() {
    return redisTemplate;
  }

  /**
   * 同步删除单个对象
   *
   * @param key Redis键名
   * @return true 表示删除成功
   */
  public Boolean delete(String key) {
    return redisTemplate.delete(key);
  }

  /**
   * 同步删除集合对象
   *
   * @param keys redis键集合
   * @return 删除成功的数量
   */
  public Long delete(Collection<String> keys) {
    return redisTemplate.delete(keys);
  }

  /**
   * 异步删除单个对象
   *
   * @param key Redis键名
   * @return true 表示删除成功
   */
  public Boolean unlink(String key) {
    return redisTemplate.unlink(key);
  }

  /**
   * 异步删除集合对象
   *
   * @param keys redis键集合
   * @return 删除成功的数量
   */
  public Long unlink(Collection<String> keys) {
    return redisTemplate.unlink(keys);
  }

  /**
   * 重命名 Redis 键名
   *
   * @param oldKey 原 Redis 键名
   * @param newKey 新 Redis 键名
   */
  public void rename(String oldKey, String newKey) {
    redisTemplate.rename(oldKey, newKey);
  }

  /**
   * 重命名 Redis 键名（仅当 newKey 不存在时，将 oldKey 重命名为 newKey）
   *
   * @param oldKey 原 Redis 键名
   * @param newKey 新 Redis 键名
   * @return true 表示重命名成功
   */
  public Boolean renameIfAbsent(String oldKey, String newKey) {
    return redisTemplate.renameIfAbsent(oldKey, newKey);
  }

  /**
   * 地理空间操作（geospatial）
   *
   * @param key Redis键名
   * @param <V> 值对象
   * @return 操作对象
   */
  public <V> BoundGeoOperations<String, V> boundGeoOps(String key) {
    return redisTemplate.boundGeoOps(key);
  }

  /**
   * 返回 Redis 数据类型
   *
   * @param key Redis键名
   * @return Redis数据类型
   */
  public DataType type(String key) {
    return redisTemplate.type(key);
  }

  /**
   * 判断是否存在 Redis 键名
   *
   * @param key Redis键名
   * @return true 表示存在
   */
  public Boolean hasKey(String key) {
    return redisTemplate.hasKey(key);
  }

  /**
   * 统计集合中存在 Redis 键名的数量
   *
   * @param keys Redis键集合
   * @return 已存在的数量
   */
  public Long countExistingKeys(Collection<String> keys) {
    return redisTemplate.countExistingKeys(keys);
  }

  /**
   * 获取指定格式的 Redis 键名列表（谨慎使用）
   *
   * @param pattern 键名格式
   * @return 对象列表
   */
  public Set<String> keys(String pattern) {
    return redisTemplate.keys(pattern);
  }

  /**
   * 哈希操作（hashes）
   *
   * @param key  Redis键名
   * @param <HK> Hash键名
   * @param <HV> Hash值对象
   * @return 操作对象
   */
  public <HK, HV> BoundHashOperations<String, HK, HV> boundHashOps(String key) {
    return redisTemplate.boundHashOps(key);
  }

  /**
   * 列表操作（lists）
   *
   * @param key Redis键名
   * @param <V> 值对象
   * @return 操作对象
   */
  public <V> BoundListOperations<String, V> boundListOps(String key) {
    return redisTemplate.boundListOps(key);
  }

  /**
   * 集合操作（sets）
   *
   * @param key Redis键名
   * @param <V> 值对象
   * @return 操作对象
   */
  public <V> BoundSetOperations<String, V> boundSetOps(String key) {
    return redisTemplate.boundSetOps(key);
  }

  /**
   * 流操作（streams） - Redis 5.0 版本新增加的数据结构，主要用于消息队列。
   *
   * @param key  Redis键名
   * @param <HK> Hash键名
   * @param <HV> Hash值对象
   * @return 操作对象
   */
  public <HK, HV> BoundStreamOperations<String, HK, HV> boundStreamOps(String key) {
    return redisTemplate.boundStreamOps(key);
  }

  /**
   * 值对象操作（strings）
   *
   * @param key Redis键名
   * @param <V> 值对象
   * @return 操作对象
   */
  public <V> BoundValueOperations<String, V> boundValueOps(String key) {
    return redisTemplate.boundValueOps(key);
  }

  /**
   * 有序集合操作（sorted sets）
   *
   * @param key Redis键名
   * @param <V> 值对象
   * @return 操作对象
   */
  @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
  public <V> BoundZSetOperations<String, V> boundZSetOps(String key) {
    return redisTemplate.boundZSetOps(key);
  }

  /**
   * 设置有效时间
   *
   * @param key     Redis键名
   * @param timeout 超时时间（单位秒）
   * @return true=设置成功；false=设置失败
   */
  public Boolean expire(String key, long timeout) {
    return expire(key, timeout, TimeUnit.SECONDS);
  }

  /**
   * 设置有效时间
   *
   * @param key      Redis键名
   * @param timeout  超时时间
   * @param timeUnit 时间单位
   * @return true=设置成功；false=设置失败
   */
  public Boolean expire(String key, long timeout, TimeUnit timeUnit) {
    return redisTemplate.expire(key, timeout, timeUnit);
  }

  /**
   * 设置有效时间
   *
   * @param key     Redis键名
   * @param timeout 超时时间
   * @return true=设置成功；false=设置失败
   */
  public Boolean expire(String key, Duration timeout) {
    return redisTemplate.expire(key, timeout);
  }

  /**
   * 设置有效期截止时间
   *
   * @param key  Redis键名
   * @param date 有效期截止时间
   * @return true=设置成功；false=设置失败
   */
  public Boolean expireAt(String key, Date date) {
    return redisTemplate.expireAt(key, date);
  }

  /**
   * 设置有效期截止时间
   *
   * @param key      Redis键名
   * @param expireAt 有效期截止时间
   * @return true=设置成功；false=设置失败
   */
  public Boolean expireAt(String key, Instant expireAt) {
    return redisTemplate.expireAt(key, expireAt);
  }

  /**
   * 获取缓存有效期
   *
   * @param key Redis键名
   * @return 时间戳
   */
  public Long getExpire(String key) {
    return redisTemplate.getExpire(key);
  }

  /**
   * 获取缓存有效期
   *
   * @param key      Redis键名
   * @param timeUnit 时间单位
   * @return 时间戳
   */
  public Long getExpire(String key, TimeUnit timeUnit) {
    return redisTemplate.getExpire(key, timeUnit);
  }

  /**
   * 缓存 Value 对象
   *
   * @param key   缓存的键
   * @param value 缓存的值
   * @param <V>   值类型
   */
  public <V> void setCacheObject(String key, V value) {
    Assert.notNull(value, "value must not be null");
    boundValueOps(key).set(value);
  }

  /**
   * 缓存 Value 对象
   *
   * @param key      缓存的键
   * @param value    缓存的值
   * @param duration 缓存有效期
   * @param <V>      值类型
   */
  public <V> void setCacheObject(String key, V value, Duration duration) {
    Assert.notNull(value, "value must not be null");
    boundValueOps(key).set(value, duration);
  }

  /**
   * 缓存 Value 对象
   *
   * @param key      缓存的键
   * @param value    缓存的值
   * @param timeout  超时时间
   * @param timeUnit 时间单位
   * @param <V>      值类型
   */
  public <V> void setCacheObject(String key, V value, long timeout, TimeUnit timeUnit) {
    Assert.notNull(value, "value must not be null");
    boundValueOps(key).set(value, timeout, timeUnit);
  }

  /**
   * 缓存 Value 对象（仅当 key 不存在时，set 才会生效）
   *
   * @param key   缓存的键
   * @param value 缓存的值
   * @param <V>   值类型
   */
  public <V> void setCacheObjectIfAbsent(String key, V value) {
    Assert.notNull(value, "value must not be null");
    boundValueOps(key).setIfAbsent(value);
  }

  /**
   * 缓存 Value 对象（仅当 key 不存在时，set 才会生效）
   *
   * @param key      缓存的键
   * @param value    缓存的值
   * @param duration 缓存有效期
   * @param <V>      值类型
   */
  public <V> void setCacheObjectIfAbsent(String key, V value, Duration duration) {
    Assert.notNull(value, "value must not be null");
    boundValueOps(key).setIfAbsent(value, duration);
  }

  /**
   * 缓存 Value 对象（仅当 key 不存在时，set 才会生效）
   *
   * @param key      缓存的键
   * @param value    缓存的值
   * @param timeout  超时时间
   * @param timeUnit 时间单位
   * @param <V>      值类型
   */
  public <V> void setCacheObjectIfAbsent(String key, V value, long timeout, TimeUnit timeUnit) {
    Assert.notNull(value, "value must not be null");
    boundValueOps(key).setIfAbsent(value, timeout, timeUnit);
  }

  /**
   * 缓存 Value 对象（仅当 key 存在时，set 才会生效）
   *
   * @param key   缓存的键
   * @param value 缓存的值
   * @param <V>   值类型
   */
  public <V> void setCacheObjectIfPresent(String key, V value) {
    Assert.notNull(value, "value must not be null");
    boundValueOps(key).setIfPresent(value);
  }

  /**
   * 缓存 Value 对象（仅当 key 存在时，set 才会生效）
   *
   * @param key      缓存的键
   * @param value    缓存的值
   * @param duration 缓存有效期
   * @param <V>      值类型
   */
  public <V> void setCacheObjectIfPresent(String key, V value, Duration duration) {
    Assert.notNull(value, "value must not be null");
    boundValueOps(key).setIfPresent(value, duration);
  }

  /**
   * 缓存 Value 对象（仅当 key 存在时，set 才会生效）
   *
   * @param key      缓存的键
   * @param value    缓存的值
   * @param timeout  超时时间
   * @param timeUnit 时间单位
   * @param <V>      值类型
   */
  public <V> void setCacheObjectIfPresent(String key, V value, long timeout, TimeUnit timeUnit) {
    Assert.notNull(value, "value must not be null");
    boundValueOps(key).setIfPresent(value, timeout, timeUnit);
  }

  /**
   * 获取缓存的 Value 对象
   *
   * @param key Redis键名
   * @param <V> 值类型
   * @return 缓存对象
   */
  public <V> V getCacheObject(String key) {
    BoundValueOperations<String, V> operations = boundValueOps(key);
    return operations.get();
  }

  /**
   * 获取缓存的 Value 对象
   *
   * @param key      Redis键名
   * @param supplier 数据提供者
   * @param <V>      值类型
   * @return 缓存对象
   */
  public <V> V getCacheObject(String key, Supplier<V> supplier) {
    BoundValueOperations<String, V> operations = boundValueOps(key);
    return Optional.ofNullable(operations.get()).orElseGet(supplier);
  }

  /**
   * 计数器，递增
   *
   * @param key Redis键名
   * @return 结果
   */
  public Long increment(String key) {
    return boundValueOps(key).increment();
  }

  /**
   * 计数器，递增
   *
   * @param key   Redis键名
   * @param delta 递增因子
   * @return 结果
   */
  public Long increment(String key, long delta) {
    return boundValueOps(key).increment(delta);
  }

  /**
   * 计数器，递减
   *
   * @param key Redis键名
   * @return 结果
   */
  public Long decrement(String key) {
    return boundValueOps(key).decrement();
  }

  /**
   * 计数器，递减
   *
   * @param key   Redis键名
   * @param delta 递减因子
   * @return 结果
   */
  public Long decrement(String key, long delta) {
    return boundValueOps(key).decrement(delta);
  }

  /**
   * 缓存 List 对象
   *
   * @param key      Redis键名
   * @param dataList 缓存的集合数据
   * @return 缓存成功计数
   */
  public <V> Long setCacheList(String key, V... dataList) {
    Assert.notEmpty(dataList, "dataList must not be empty");
    BoundListOperations<String, V> operations = boundListOps(key);
    return operations.rightPushAll(dataList);
  }

  /**
   * 缓存 List 对象
   *
   * @param key      Redis键名
   * @param dataList 缓存的集合数据
   * @return 缓存成功计数
   */
  public <V> Long setCacheList(String key, List<V> dataList) {
    Assert.notEmpty(dataList, "dataList must not be empty");
    return redisTemplate.opsForList().rightPushAll(key, dataList);
  }

  /**
   * 获取缓存的 List 对象
   *
   * @param key Redis键名
   * @param <V> 值类型
   * @return 缓存的集合数据
   */
  public <V> List<V> getCacheList(String key) {
    BoundListOperations<String, V> operations = boundListOps(key);
    return operations.range(0, -1);
  }

  /**
   * 获取缓存的 List 对象
   *
   * @param key      Redis键名
   * @param supplier 数据提供者
   * @param <V>      值类型
   * @return 缓存的集合数据
   */
  public <V> List<V> getCacheList(String key, Supplier<List<V>> supplier) {
    BoundListOperations<String, V> operations = boundListOps(key);
    return Optional.ofNullable(operations.range(0, -1)).orElseGet(supplier);
  }

  /**
   * 缓存的 Set 对象
   *
   * @param key     Redis键名
   * @param dataSet 缓存的集合数据
   * @return 缓存成功计数
   */
  public <V> Long setCacheSet(String key, V... dataSet) {
    Assert.notEmpty(dataSet, "dataSet must not be empty");
    BoundSetOperations<String, V> operations = boundSetOps(key);
    return operations.add(dataSet);
  }

  /**
   * 缓存的 Set 对象
   *
   * @param key     Redis键名
   * @param dataSet 缓存的集合数据
   * @return 缓存成功计数
   */
  public <V> Long setCacheSet(String key, Set<V> dataSet) {
    Assert.notEmpty(dataSet, "dataSet must not be empty");
    BoundSetOperations<String, V> operations = boundSetOps(key);
    return dataSet.stream().map(v -> operations.add(v)).count();
  }

  /**
   * 获取缓存的 Set 对象
   *
   * @param key Redis键名
   * @param <V> 值类型
   * @return 缓存的集合数据
   */
  public <V> Set<V> getCacheSet(String key) {
    BoundSetOperations<String, V> operations = boundSetOps(key);
    return operations.members();
  }

  /**
   * 获取缓存的 Set 对象
   *
   * @param key      Redis键名
   * @param supplier 数据提供者
   * @param <V>      值类型
   * @return 缓存的集合数据
   */
  public <V> Set<V> getCacheSet(String key, Supplier<Set<V>> supplier) {
    BoundSetOperations<String, V> operations = boundSetOps(key);
    return Optional.ofNullable(operations.members()).orElseGet(supplier);
  }

  /**
   * 缓存的 Map 对象
   *
   * @param key     Redis键名
   * @param dataMap 缓存的集合数据
   * @param <V>     值类型
   */
  public <V> void setCacheMap(String key, Map<String, V> dataMap) {
    Assert.notEmpty(dataMap, "dataMap must not be empty");
    boundHashOps(key).putAll(dataMap);
  }

  /**
   * 缓存的 Map 对象
   *
   * @param key   Redis键名
   * @param hKey  HK
   * @param value 缓存的数据
   * @param <V>   值类型
   */
  public <V> void setCacheMapValue(String key, String hKey, V value) {
    Assert.notNull(value, "value must not be null");
    BoundHashOperations<String, String, V> operations = boundHashOps(key);
    operations.put(hKey, value);
  }

  /**
   * 获取缓存的 Map 对象
   *
   * @param key Redis键名
   * @param <V> 值类型
   * @return 缓存的集合数据
   */
  public <V> Map<String, V> getCacheMap(String key) {
    BoundHashOperations<String, String, V> operations = boundHashOps(key);
    return operations.entries();
  }

  /**
   * 获取缓存的 Map 对象，指定某个 hKey 的结果
   *
   * @param key  Redis键名
   * @param hKey HK
   * @param <V>  值类型
   * @return 缓存的数据
   */
  public <V> V getCacheMapValue(String key, String hKey) {
    BoundHashOperations<String, String, V> operations = boundHashOps(key);
    return operations.get(hKey);
  }

  /**
   * 获取缓存的 Map 对象，指定多个 hKeys 的结果
   *
   * @param key   Redis键名
   * @param hKeys HKs
   * @param <V>   值类型
   * @return 缓存的集合数据
   */
  public <V> List<V> getMultiCacheMapValue(String key, Collection<String> hKeys) {
    Assert.notEmpty(hKeys, "hKeys must not be empty");
    BoundHashOperations<String, String, V> operations = boundHashOps(key);
    return operations.multiGet(hKeys);
  }

  /**
   * 获取缓存的 Map 对象，指定多个 hKeys 的结果
   *
   * @param key   Redis键名
   * @param hKeys HKs
   * @param <V>   值类型
   * @return 缓存的集合数据
   */
  public <V> List<V> getMultiCacheMapValue(String key, String... hKeys) {
    Assert.notEmpty(hKeys, "hKeys must not be empty");
    return getMultiCacheMapValue(key, Arrays.asList(hKeys));
  }

  /**
   * 删除缓存的 Map 对象，指定多个 hKeys 的数据
   *
   * @param key   Redis键名
   * @param hKeys HKs
   * @param <V>   值类型
   * @return 删除成功的计数
   */
  public <V> Long deleteCacheMapValue(String key, Collection<String> hKeys) {
    Assert.notEmpty(hKeys, "hKeys must not be empty");
    BoundHashOperations<String, String, V> operations = boundHashOps(key);
    return hKeys.stream().map(k -> operations.delete(k)).count();
  }

  /**
   * 删除缓存的 Map 对象，指定多个 hKeys 的数据
   *
   * @param key   Redis键名
   * @param hKeys HKs
   * @return 删除成功的计数
   */
  public Long deleteCacheMapValue(String key, String... hKeys) {
    Assert.notEmpty(hKeys, "hKeys must not be empty");
    return deleteCacheMapValue(key, Arrays.asList(hKeys));
  }

}

