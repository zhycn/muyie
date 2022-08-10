package com.tsollu.redis;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 *
 * @author larry.qi
 **/
@Component
public class RedisCache {
    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键
     * @param value 缓存的值
     */
    public <V> void setCacheObject(final String key, final V value) {
        Assert.notNull(value, "value must not be null");
        boundValueOps(key).set(value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键
     * @param value    缓存的值
     * @param timeout  超时时间
     * @param timeUnit 时间单位
     */
    public <V> void setCacheObject(final String key, final V value, final Integer timeout, final TimeUnit timeUnit) {
        Assert.notNull(value, "value must not be null");
        boundValueOps(key).set(value, timeout, timeUnit);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key     缓存的键
     * @param value   缓存的值
     * @param timeout 超时时间
     */
    public <V> void setCacheObject(final String key, final V value, final Duration timeout) {
        Assert.notNull(value, "value must not be null");
        boundValueOps(key).set(value, timeout);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <V> V getCacheObject(final String key) {
        BoundValueOperations<String, V> operations = boundValueOps(key);
        return operations.get();
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间（单位秒）
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key      Redis键
     * @param timeout  超时时间
     * @param timeUnit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final Duration timeout) {
        return redisTemplate.expire(key, timeout);
    }

    /**
     * 设置有效期截止时间
     *
     * @param key  Redis键
     * @param date 有效期截止时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 设置有效期截止时间
     *
     * @param key      Redis键
     * @param expireAt 有效期截止时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expireAt(String key, Instant expireAt) {
        return redisTemplate.expireAt(key, expireAt);
    }

    /**
     * 删除单个对象
     *
     * @param key Redis键
     */
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param keys Redis键集合
     * @return 删除的数量
     */
    public long deleteObject(final Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 缓存List对象
     *
     * @param key      Redis键
     * @param dataList 缓存的集合数据
     * @return 缓存的记录数
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Assert.notEmpty(dataList, "dataList must not be empty");
        Long count = boundListOps(key).rightPushAll(dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的List对象
     *
     * @param key Redis键
     * @return 缓存的集合数据
     */
    public <V> List<V> getCacheList(final String key) {
        BoundListOperations<String, V> operations = boundListOps(key);
        return operations.range(0, -1);
    }

    /**
     * 缓存的Set对象
     *
     * @param key     Redis键
     * @param dataSet 缓存的集合数据
     * @return 缓存数据的对象
     */
    public <V> BoundSetOperations<String, V> setCacheSet(final String key, final Set<V> dataSet) {
        Assert.notEmpty(dataSet, "dataSet must not be empty");
        BoundSetOperations<String, V> operations = boundSetOps(key);
        dataSet.forEach(e -> operations.add(e));
        return operations;
    }

    /**
     * 获得缓存的Set对象
     *
     * @param key Redis键
     * @return 缓存的集合数据
     */
    public <V> Set<V> getCacheSet(final String key) {
        BoundSetOperations<String, V> operations = boundSetOps(key);
        return operations.members();
    }

    /**
     * 缓存的Map对象
     *
     * @param key     Redis键
     * @param dataMap 缓存的集合数据
     */
    public <V> void setCacheMap(final String key, final Map<String, V> dataMap) {
        Assert.notEmpty(dataMap, "dataMap must not be empty");
        boundHashOps(key).putAll(dataMap);
    }

    /**
     * 获得缓存的Map对象
     *
     * @param key Redis键
     * @return 缓存的集合数据
     */
    public <V> Map<String, V> getCacheMap(final String key) {
        BoundHashOperations<String, String, V> operations = boundHashOps(key);
        return operations.entries();
    }

    /**
     * 往缓存的Map对象中写入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value Hash值
     */
    public <V> void setCacheMapValue(final String key, final String hKey, final V value) {
        Assert.notNull(value, "value must not be null");
        BoundHashOperations<String, String, V> operations = boundHashOps(key);
        operations.put(hKey, value);
    }

    /**
     * 获取缓存的Map对象的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <V> V getCacheMapValue(final String key, final String hKey) {
        BoundHashOperations<String, String, V> operations = boundHashOps(key);
        return operations.get(hKey);
    }

    /**
     * 获取缓存的Map对象的多个数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <V> List<V> getMultiCacheMapValue(final String key, final Collection<String> hKeys) {
        Assert.notEmpty(hKeys, "hKeys must not be empty");
        BoundHashOperations<String, String, V> operations = boundHashOps(key);
        return operations.multiGet(hKeys);
    }

    /**
     * 获取缓存的Map对象的多个数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <V> List<V> getMultiCacheMapValue(final String key, final String... hKeys) {
        return getMultiCacheMapValue(key, Arrays.asList(hKeys));
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 判断是否存在Redis键
     *
     * @param key Redis键
     * @return true 表示存在
     */
    public boolean hasKey(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 地理空间操作（geospatial）
     *
     * @param key Redis键
     * @param <V> 值对象
     * @return 操作对象
     */
    public <V> BoundGeoOperations<String, V> boundGeoOps(final String key) {
        return redisTemplate.boundGeoOps(key);
    }

    /**
     * 哈希操作（hashes）
     *
     * @param key  Redis键
     * @param <HK> Hash键
     * @param <HV> Hash值
     * @return 操作对象
     */
    public <HK, HV> BoundHashOperations<String, HK, HV> boundHashOps(final String key) {
        return redisTemplate.boundHashOps(key);
    }

    /**
     * 集合操作（sets）
     *
     * @param key Redis键
     * @param <V> 值对象
     * @return 操作对象
     */
    public <V> BoundSetOperations<String, V> boundSetOps(final String key) {
        return redisTemplate.boundSetOps(key);
    }

    /**
     * 列表操作（lists）
     *
     * @param key Redis键
     * @param <V> 值对象
     * @return 操作对象
     */
    public <V> BoundListOperations<String, V> boundListOps(final String key) {
        return redisTemplate.boundListOps(key);
    }

    /**
     * 流操作（streams） - Redis 5.0 版本新增加的数据结构，主要用于消息队列。
     *
     * @param key  Redis键
     * @param <HK> Hash键
     * @param <HV> Hash值
     * @return 操作对象
     */
    public <HK, HV> BoundStreamOperations<String, HK, HV> boundStreamOps(final String key) {
        return redisTemplate.boundStreamOps(key);
    }

    /**
     * 有序集合操作（sorted sets）
     *
     * @param key Redis键
     * @param <V> 值对象
     * @return 操作对象
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public <V> BoundZSetOperations<String, V> boundZSetOps(final String key) {
        return redisTemplate.boundZSetOps(key);
    }

    /**
     * 值对象操作（strings）
     *
     * @param key Redis键
     * @param <V> 值对象
     * @return 操作对象
     */
    public <V> BoundValueOperations<String, V> boundValueOps(final String key) {
        return redisTemplate.boundValueOps(key);
    }

}

