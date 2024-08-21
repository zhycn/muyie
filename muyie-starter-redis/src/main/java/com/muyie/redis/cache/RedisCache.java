package com.muyie.redis.cache;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * RedisCache - 整合了一些常用接口
 *
 * @author larry.qi
 * @since 2.7.13
 **/
public class RedisCache<V> {

  private final RedisTemplate<String, V> redisTemplate;

  public RedisCache(RedisTemplate<String, V> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  /**
   * 获取 RedisTemplate 对象，使用原生接口能力
   *
   * @return RedisTemplate 对象
   */
  public RedisTemplate<String, V> getTemplate() {
    return Objects.requireNonNull(redisTemplate);
  }

  /**
   * 同步删除单个对象
   *
   * @param key 键
   * @return true 表示删除成功
   */
  public Boolean delete(String key) {
    return getTemplate().delete(key);
  }

  /**
   * 同步删除集合对象
   *
   * @param keys 键
   * @return 删除成功的数量
   */
  public Long delete(Collection<String> keys) {
    return getTemplate().delete(keys);
  }

  /**
   * 异步删除单个对象
   *
   * @param key 键
   * @return true 表示删除成功
   */
  public Boolean unlink(String key) {
    return getTemplate().unlink(key);
  }

  /**
   * 异步删除集合对象
   *
   * @param keys 键
   * @return 删除成功的数量
   */
  public Long unlink(Collection<String> keys) {
    return getTemplate().unlink(keys);
  }

  /**
   * 重命名
   *
   * @param oldKey 老键
   * @param newKey 新键
   */
  public void rename(String oldKey, String newKey) {
    getTemplate().rename(oldKey, newKey);
  }

  /**
   * 重命名（仅当 newKey 不存在时，将 oldKey 重命名为 newKey）
   *
   * @param oldKey 老键
   * @param newKey 新键
   * @return true 表示重命名成功
   */
  public Boolean renameIfAbsent(String oldKey, String newKey) {
    return getTemplate().renameIfAbsent(oldKey, newKey);
  }

  /**
   * 返回 Redis 数据类型
   *
   * @param key 键
   * @return Redis数据类型
   */
  public DataType type(String key) {
    return getTemplate().type(key);
  }

  /**
   * 判断 key 是否存在
   *
   * @param key 键
   * @return true 表示存在
   */
  public Boolean hasKey(String key) {
    return getTemplate().hasKey(key);
  }

  /**
   * 统计集合中存在 keys 的数量
   *
   * @param keys 键
   * @return 已存在的数量
   */
  public Long countExistingKeys(Collection<String> keys) {
    return getTemplate().countExistingKeys(keys);
  }

  /**
   * 获取指定格式的 key 列表（谨慎使用）
   *
   * @param pattern 键的格式
   * @return 键的列表
   */
  public Set<String> keys(String pattern) {
    return getTemplate().keys(pattern);
  }

  /**
   * 设置缓存的有效时间
   *
   * @param key     键
   * @param timeout 超时时间（单位秒）
   * @return true=设置成功；false=设置失败
   */
  public Boolean expire(String key, long timeout) {
    return expire(key, timeout, TimeUnit.SECONDS);
  }

  /**
   * 设置缓存的有效时间
   *
   * @param key      键
   * @param timeout  超时时间
   * @param timeUnit 时间单位
   * @return true=设置成功；false=设置失败
   */
  public Boolean expire(String key, long timeout, TimeUnit timeUnit) {
    return getTemplate().expire(key, timeout, timeUnit);
  }

  /**
   * 设置缓存的有效时间
   *
   * @param key     键
   * @param timeout 超时时间
   * @return true=设置成功；false=设置失败
   */
  public Boolean expire(String key, Duration timeout) {
    return getTemplate().expire(key, timeout);
  }

  /**
   * 设置有效期截止时间
   *
   * @param key  键
   * @param date 有效期截止时间
   * @return true=设置成功；false=设置失败
   */
  public Boolean expireAt(String key, Date date) {
    return getTemplate().expireAt(key, date);
  }

  /**
   * 设置有效期截止时间
   *
   * @param key      键
   * @param expireAt 有效期截止时间
   * @return true=设置成功；false=设置失败
   */
  public Boolean expireAt(String key, Instant expireAt) {
    return getTemplate().expireAt(key, expireAt);
  }

  /**
   * 获取缓存有效期
   *
   * @param key 键
   * @return 时间戳
   */
  public Long getExpire(String key) {
    return getTemplate().getExpire(key);
  }

  /**
   * 获取缓存有效期
   *
   * @param key      键
   * @param timeUnit 时间单位
   * @return 时间戳
   */
  public Long getExpire(String key, TimeUnit timeUnit) {
    return getTemplate().getExpire(key, timeUnit);
  }

  /**
   * 地理空间操作（geospatial）
   *
   * @param key 键
   * @return 操作对象
   */
  public BoundGeoOperations<String, V> boundGeoOps(String key) {
    return getTemplate().boundGeoOps(key);
  }

  /**
   * 哈希操作（hashes）
   *
   * @param key  键
   * @param <HK> Hash键
   * @param <HV> Hash值对象
   * @return 操作对象
   */
  public <HK, HV> BoundHashOperations<String, HK, HV> boundHashOps(String key) {
    return getTemplate().boundHashOps(key);
  }

  /**
   * 列表操作（lists）
   *
   * @param key 键
   * @return 操作对象
   */
  public BoundListOperations<String, V> boundListOps(String key) {
    return getTemplate().boundListOps(key);
  }

  /**
   * 集合操作（sets）
   *
   * @param key 键
   * @return 操作对象
   */
  public BoundSetOperations<String, V> boundSetOps(String key) {
    return getTemplate().boundSetOps(key);
  }

  /**
   * 流操作（streams） - Redis 5.0 版本新增加的数据结构，主要用于消息队列。
   *
   * @param key  键
   * @param <HK> Hash键
   * @param <HV> Hash值对象
   * @return 操作对象
   */
  public <HK, HV> BoundStreamOperations<String, HK, HV> boundStreamOps(String key) {
    return getTemplate().boundStreamOps(key);
  }

  /**
   * 值对象操作（strings）
   *
   * @param key 键
   * @return 操作对象
   */
  public BoundValueOperations<String, V> boundValueOps(String key) {
    return getTemplate().boundValueOps(key);
  }

  /**
   * 有序集合操作（sorted sets）
   *
   * @param key 键
   * @return 操作对象
   */
  public BoundZSetOperations<String, V> boundZSetOps(String key) {
    return getTemplate().boundZSetOps(key);
  }

  /**
   * 计数器，递增
   *
   * @param key 键
   * @return 结果
   */
  public Long increment(String key) {
    return boundValueOps(key).increment();
  }

  /**
   * 计数器，递增
   *
   * @param key   键
   * @param delta 递增因子
   * @return 结果
   */
  public Long increment(String key, long delta) {
    return boundValueOps(key).increment(delta);
  }

  /**
   * 计数器，递减
   *
   * @param key 键
   * @return 结果
   */
  public Long decrement(String key) {
    return boundValueOps(key).decrement();
  }

  /**
   * 计数器，递减
   *
   * @param key   键
   * @param delta 递减因子
   * @return 结果
   */
  public Long decrement(String key, long delta) {
    return boundValueOps(key).decrement(delta);
  }

  /**
   * 缓存 Value 对象
   *
   * @param key   键
   * @param value 值
   * @return BoundValueOperations
   */
  public BoundValueOperations<String, V> setValueCache(String key, V value) {
    BoundValueOperations<String, V> boundValueOperations = boundValueOps(key);
    boundValueOperations.set(value);
    return boundValueOperations;
  }

  /**
   * 缓存 Value 对象
   *
   * @param key     键
   * @param value   值
   * @param timeout 超时时间
   * @return BoundValueOperations
   */
  public BoundValueOperations<String, V> setValueCache(String key, V value, Duration timeout) {
    BoundValueOperations<String, V> boundValueOperations = boundValueOps(key);
    boundValueOperations.set(value, timeout);
    return boundValueOperations;
  }

  /**
   * 缓存 Value 对象
   *
   * @param key      键
   * @param value    值
   * @param timeout  超时时间
   * @param timeUnit 时间单位
   * @return BoundValueOperations
   */
  public BoundValueOperations<String, V> setValueCache(String key, V value, long timeout, TimeUnit timeUnit) {
    BoundValueOperations<String, V> boundValueOperations = boundValueOps(key);
    boundValueOperations.set(value, timeout, timeUnit);
    return boundValueOperations;
  }

  /**
   * 缓存 Value 对象
   *
   * @param key   键
   * @param value 值
   * @param date  有效期截止时间
   * @return BoundValueOperations
   */
  public BoundValueOperations<String, V> setValueCache(String key, V value, Date date) {
    BoundValueOperations<String, V> boundValueOperations = setValueCache(key, value);
    boundValueOperations.expireAt(date);
    return boundValueOperations;
  }

  /**
   * 缓存 Value 对象
   *
   * @param key      键
   * @param value    值
   * @param expireAt 有效期截止时间
   * @return BoundValueOperations
   */
  public BoundValueOperations<String, V> setValueCache(String key, V value, Instant expireAt) {
    BoundValueOperations<String, V> boundValueOperations = setValueCache(key, value);
    boundValueOperations.expireAt(expireAt);
    return boundValueOperations;
  }

  /**
   * 缓存 Value 对象（仅当 key 不存在时，set 才会生效）
   *
   * @param key   键
   * @param value 值
   * @return BoundValueOperations
   */
  public BoundValueOperations<String, V> setValueCacheIfAbsent(String key, V value) {
    BoundValueOperations<String, V> boundValueOperations = boundValueOps(key);
    boundValueOperations.setIfAbsent(value);
    return boundValueOperations;
  }

  /**
   * 缓存 Value 对象（仅当 key 不存在时，set 才会生效）
   *
   * @param key     键
   * @param value   值
   * @param timeout 超时时间
   * @return BoundValueOperations
   */
  public BoundValueOperations<String, V> setValueCacheIfAbsent(String key, V value, Duration timeout) {
    BoundValueOperations<String, V> boundValueOperations = boundValueOps(key);
    boundValueOperations.setIfAbsent(value, timeout);
    return boundValueOperations;
  }

  /**
   * 缓存 Value 对象（仅当 key 不存在时，set 才会生效）
   *
   * @param key      键
   * @param value    值
   * @param timeout  超时时间
   * @param timeUnit 时间单位
   * @return BoundValueOperations
   */
  public BoundValueOperations<String, V> setValueCacheIfAbsent(String key, V value, long timeout, TimeUnit timeUnit) {
    BoundValueOperations<String, V> boundValueOperations = boundValueOps(key);
    boundValueOperations.setIfAbsent(value, timeout, timeUnit);
    return boundValueOperations;
  }

  /**
   * 缓存 Value 对象（仅当 key 不存在时，set 才会生效）
   *
   * @param key   键
   * @param value 值
   * @param date  有效期截止时间
   * @return BoundValueOperations
   */
  public BoundValueOperations<String, V> setValueCacheIfAbsent(String key, V value, Date date) {
    BoundValueOperations<String, V> boundValueOperations = boundValueOps(key);
    Boolean flag = boundValueOperations.setIfAbsent(value);
    if (Objects.nonNull(flag) && flag) {
      boundValueOperations.expireAt(date);
    }
    return boundValueOperations;
  }

  /**
   * 缓存 Value 对象（仅当 key 不存在时，set 才会生效）
   *
   * @param key      键
   * @param value    值
   * @param expireAt 有效期截止时间
   * @return BoundValueOperations
   */
  public BoundValueOperations<String, V> setValueCacheIfAbsent(String key, V value, Instant expireAt) {
    BoundValueOperations<String, V> boundValueOperations = boundValueOps(key);
    Boolean flag = boundValueOperations.setIfAbsent(value);
    if (Objects.nonNull(flag) && flag) {
      boundValueOperations.expireAt(expireAt);
    }
    return boundValueOperations;
  }

  /**
   * 缓存 Value 对象（仅当 key 存在时，set 才会生效）
   *
   * @param key   缓存的键
   * @param value 缓存的值
   * @return BoundValueOperations
   */
  public BoundValueOperations<String, V> setValueCacheIfPresent(String key, V value) {
    BoundValueOperations<String, V> boundValueOperations = boundValueOps(key);
    boundValueOperations.setIfPresent(value);
    return boundValueOperations;
  }

  /**
   * 缓存 Value 对象（仅当 key 存在时，set 才会生效）
   *
   * @param key     键
   * @param value   值
   * @param timeout 超时时间
   * @return BoundValueOperations
   */
  public BoundValueOperations<String, V> setValueCacheIfPresent(String key, V value, Duration timeout) {
    BoundValueOperations<String, V> boundValueOperations = boundValueOps(key);
    boundValueOperations.setIfPresent(value, timeout);
    return boundValueOperations;
  }

  /**
   * 缓存 Value 对象（仅当 key 存在时，set 才会生效）
   *
   * @param key      键
   * @param value    值
   * @param timeout  超时时间
   * @param timeUnit 时间单位
   * @return BoundValueOperations
   */
  public BoundValueOperations<String, V> setValueCacheIfPresent(String key, V value, long timeout, TimeUnit timeUnit) {
    BoundValueOperations<String, V> boundValueOperations = boundValueOps(key);
    boundValueOperations.setIfPresent(value, timeout, timeUnit);
    return boundValueOperations;
  }

  /**
   * 缓存 Value 对象（仅当 key 存在时，set 才会生效）
   *
   * @param key   键
   * @param value 值
   * @param date  有效期截止时间
   * @return BoundValueOperations
   */
  public BoundValueOperations<String, V> setValueCacheIfPresent(String key, V value, Date date) {
    BoundValueOperations<String, V> boundValueOperations = boundValueOps(key);
    Boolean flag = boundValueOperations.setIfPresent(value);
    if (Objects.nonNull(flag) && flag) {
      boundValueOperations.expireAt(date);
    }
    return boundValueOperations;
  }

  /**
   * 缓存 Value 对象（仅当 key 存在时，set 才会生效）
   *
   * @param key      键
   * @param value    值
   * @param expireAt 有效期截止时间
   * @return BoundValueOperations
   */
  public BoundValueOperations<String, V> setValueCacheIfPresent(String key, V value, Instant expireAt) {
    BoundValueOperations<String, V> boundValueOperations = boundValueOps(key);
    Boolean flag = boundValueOperations.setIfPresent(value);
    if (Objects.nonNull(flag) && flag) {
      boundValueOperations.expireAt(expireAt);
    }
    return boundValueOperations;
  }

  /**
   * 获取缓存的 Value 对象
   *
   * @param key 键
   * @return 缓存对象
   */
  public V getValueCache(String key) {
    return boundValueOps(key).get();
  }

  /**
   * 获取缓存的 Value 对象
   *
   * @param key      键
   * @param supplier 数据提供者
   * @return 缓存对象
   */
  public V getValueCache(String key, Supplier<V> supplier) {
    return Optional.ofNullable(boundValueOps(key).get()).orElseGet(supplier);
  }

  /**
   * 缓存 List 对象
   *
   * @param key      键
   * @param dataList 缓存的集合数据
   * @return BoundListOperations
   */
  public BoundListOperations<String, V> setListCache(String key, V[] dataList) {
    return setListCache(key, Arrays.asList(dataList));
  }

  /**
   * 缓存 List 对象
   *
   * @param key      键
   * @param dataList 缓存的集合数据
   * @return BoundListOperations
   */
  public BoundListOperations<String, V> setListCache(String key, List<V> dataList) {
    getTemplate().opsForList().rightPushAll(key, dataList);
    return boundListOps(key);
  }

  /**
   * 获取缓存的 List 对象
   *
   * @param key 键
   * @return 缓存的集合数据
   */
  public List<V> getListCache(String key) {
    return boundListOps(key).range(0, -1);
  }

  /**
   * 获取缓存的 List 对象
   *
   * @param key      键
   * @param supplier 数据提供者
   * @return 缓存的集合数据
   */
  public List<V> getListCache(String key, Supplier<List<V>> supplier) {
    List<V> listCache = getListCache(key);
    return CollectionUtils.isEmpty(listCache) ? supplier.get() : listCache;
  }

  /**
   * 缓存的 Set 对象
   *
   * @param key     键
   * @param dataSet 缓存的集合数据
   * @return BoundSetOperations
   */
  public BoundSetOperations<String, V> setSetCache(String key, V[] dataSet) {
    BoundSetOperations<String, V> boundSetOperations = boundSetOps(key);
    boundSetOperations.add(dataSet);
    return boundSetOperations;
  }

  /**
   * 获取缓存的 Set 对象
   *
   * @param key 键
   * @return 缓存的集合数据
   */
  public Set<V> getSetCache(String key) {
    return boundSetOps(key).members();
  }

  /**
   * 获取缓存的 Set 对象
   *
   * @param key      键
   * @param supplier 数据提供者
   * @return 缓存的集合数据
   */
  public Set<V> getSetCache(String key, Supplier<Set<V>> supplier) {
    Set<V> setCache = getSetCache(key);
    return CollectionUtils.isEmpty(setCache) ? supplier.get() : setCache;
  }

  /**
   * 缓存的 Map 对象
   *
   * @param key     键
   * @param dataMap 缓存的集合数据
   * @return BoundHashOperations
   */
  public BoundHashOperations<String, String, V> setMapCache(String key, Map<String, V> dataMap) {
    BoundHashOperations<String, String, V> boundHashOperations = boundHashOps(key);
    boundHashOperations.putAll(dataMap);
    return boundHashOperations;
  }

  /**
   * 缓存的 Map 对象
   *
   * @param key   键
   * @param hKey  Hash键
   * @param value 缓存的数据
   * @return BoundHashOperations
   */
  public BoundHashOperations<String, String, V> setMapCache(String key, String hKey, V value) {
    BoundHashOperations<String, String, V> boundHashOperations = boundHashOps(key);
    boundHashOperations.put(hKey, value);
    return boundHashOperations;
  }

  /**
   * 获取缓存的 Map 对象
   *
   * @param key 键
   * @return 缓存的集合数据
   */
  public Map<String, V> getMapCache(String key) {
    BoundHashOperations<String, String, V> operations = boundHashOps(key);
    return operations.entries();
  }

  /**
   * 获取缓存的 Map 对象
   *
   * @param key      键
   * @param supplier 数据提供者
   * @return 缓存的数据
   */
  public Map<String, V> getMapCache(String key, Supplier<Map<String, V>> supplier) {
    Map<String, V> mapCache = getMapCache(key);
    return CollectionUtils.isEmpty(mapCache) ? supplier.get() : mapCache;
  }

  /**
   * 获取缓存的 Map 对象，指定某个 hKey 的结果
   *
   * @param key  键
   * @param hKey Hash键
   * @return 缓存的数据
   */
  public V getMapCache(String key, String hKey) {
    BoundHashOperations<String, String, V> boundHashOperations = boundHashOps(key);
    return boundHashOperations.get(hKey);
  }

  /**
   * 获取缓存的 Map 对象，指定某个 hKey 的结果
   *
   * @param key      键
   * @param hKey     Hash键
   * @param supplier 数据提供者
   * @return 缓存的数据
   */
  public V getMapCache(String key, String hKey, Supplier<V> supplier) {
    return Optional.ofNullable(getMapCache(key, hKey)).orElseGet(supplier);
  }

  /**
   * 获取缓存的 Map 对象，指定多个 hKeys 的结果
   *
   * @param key   键
   * @param hKeys Hash键
   * @return 缓存的集合数据
   */
  public List<V> getMultiMapCache(String key, Collection<String> hKeys) {
    BoundHashOperations<String, String, V> boundHashOperations = boundHashOps(key);
    return boundHashOperations.multiGet(hKeys);
  }

  /**
   * 获取缓存的 Map 对象，指定多个 hKeys 的结果
   *
   * @param key   键
   * @param hKeys Hash键
   * @return 缓存的集合数据
   */
  public List<V> getMultiMapCache(String key, String... hKeys) {
    return getMultiMapCache(key, Arrays.asList(hKeys));
  }

  /**
   * 删除缓存的 Map 对象，指定多个 hKeys 的数据
   *
   * @param key   键
   * @param hKeys Hash键
   * @return 删除成功的计数
   */
  public Long deleteMapCache(String key, Collection<String> hKeys) {
    return hKeys.stream().map(k -> boundHashOps(key).delete(k)).count();
  }

  /**
   * 删除缓存的 Map 对象，指定多个 hKeys 的数据
   *
   * @param key   键
   * @param hKeys Hash键
   * @return 删除成功的计数
   */
  public Long deleteMapCache(String key, String... hKeys) {
    return deleteMapCache(key, Arrays.asList(hKeys));
  }

}

