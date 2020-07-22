package org.slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.ttl.TransmittableThreadLocal;

import org.slf4j.spi.MDCAdapter;

import ch.qos.logback.classic.util.LogbackMDCAdapter;

/**
 * 重写{@link LogbackMDCAdapter}类，搭配TransmittableThreadLocal实现父子线程之间的数据传递
 */
public class TtlMDCAdapter implements MDCAdapter {
  private final ThreadLocal<Map<String, String>> copyOnInheritThreadLocal = new TransmittableThreadLocal<>();

  private static final int WRITE_OPERATION = 1;
  private static final int MAP_COPY_OPERATION = 2;

  private static TtlMDCAdapter ttlMDCAdapter;

  /**
   * keeps track of the last operation performed
   */
  private final ThreadLocal<Integer> lastOperation = new ThreadLocal<>();

  static {
    ttlMDCAdapter = new TtlMDCAdapter();
    MDC.mdcAdapter = ttlMDCAdapter;
  }

  public static MDCAdapter getInstance() {
    return ttlMDCAdapter;
  }

  private Integer getAndSetLastOperation(final int op) {
    final Integer lastOp = lastOperation.get();
    lastOperation.set(op);
    return lastOp;
  }

  private static boolean wasLastOpReadOrNull(final Integer lastOp) {
    return lastOp == null || lastOp == MAP_COPY_OPERATION;
  }

  private Map<String, String> duplicateAndInsertNewMap(final Map<String, String> oldMap) {
    final Map<String, String> newMap = Collections.synchronizedMap(new HashMap<>());
    if (oldMap != null) {
      // we don't want the parent thread modifying oldMap while we are
      // iterating over it
      synchronized (oldMap) {
        newMap.putAll(oldMap);
      }
    }

    copyOnInheritThreadLocal.set(newMap);
    return newMap;
  }

  /**
   * Put a context value (the <code>val</code> parameter) as identified with the
   * <code>key</code> parameter into the current thread's context map. Note that
   * contrary to log4j, the <code>val</code> parameter can be null.
   * 
   * If the current thread does not have a context map it is created as a side
   * effect of this call.
   *
   * @throws IllegalArgumentException in case the "key" parameter is null
   */
  @Override
  public void put(final String key, final String val) {
    if (key == null) {
      throw new IllegalArgumentException("key cannot be null");
    }

    final Map<String, String> oldMap = copyOnInheritThreadLocal.get();
    final Integer lastOp = getAndSetLastOperation(WRITE_OPERATION);

    if (wasLastOpReadOrNull(lastOp) || oldMap == null) {
      final Map<String, String> newMap = duplicateAndInsertNewMap(oldMap);
      newMap.put(key, val);
    } else {
      oldMap.put(key, val);
    }
  }

  /**
   * Remove the the context identified by the <code>key</code> parameter.
   */
  @Override
  public void remove(final String key) {
    if (key == null) {
      return;
    }
    final Map<String, String> oldMap = copyOnInheritThreadLocal.get();
    if (oldMap == null) {
      return;
    }

    final Integer lastOp = getAndSetLastOperation(WRITE_OPERATION);

    if (wasLastOpReadOrNull(lastOp)) {
      final Map<String, String> newMap = duplicateAndInsertNewMap(oldMap);
      newMap.remove(key);
    } else {
      oldMap.remove(key);
    }

  }

  /**
   * Clear all entries in the MDC.
   */
  @Override
  public void clear() {
    lastOperation.set(WRITE_OPERATION);
    copyOnInheritThreadLocal.remove();
  }

  /**
   * Get the context identified by the <code>key</code> parameter.
   * 
   * @return the context
   */
  @Override
  public String get(final String key) {
    final Map<String, String> map = copyOnInheritThreadLocal.get();
    if ((map != null) && (key != null)) {
      return map.get(key);
    } else {
      return null;
    }
  }

  /**
   * Get the current thread's MDC as a map. This method is intended to be used
   * internally.
   * 
   * @return the current thread's MDC as a map
   */
  public Map<String, String> getPropertyMap() {
    lastOperation.set(MAP_COPY_OPERATION);
    return copyOnInheritThreadLocal.get();
  }

  /**
   * Returns the keys in the MDC as a {@link Set}. The returned value can be null.
   * 
   * @return the keys
   */
  public Set<String> getKeys() {
    final Map<String, String> map = getPropertyMap();

    if (map != null) {
      return map.keySet();
    } else {
      return null;
    }
  }

  /**
   * Return a copy of the current thread's context map. Returned value may be
   * null.
   * 
   * @return a copy of the current thread's context map.
   */
  @Override
  public Map<String, String> getCopyOfContextMap() {
    final Map<String, String> hashMap = copyOnInheritThreadLocal.get();
    if (hashMap == null) {
      return null;
    } else {
      return new HashMap<>(hashMap);
    }
  }

  @Override
  public void setContextMap(final Map<String, String> contextMap) {
    lastOperation.set(WRITE_OPERATION);

    final Map<String, String> newMap = Collections.synchronizedMap(new HashMap<>());
    newMap.putAll(contextMap);

    // the newMap replaces the old one for serialisation's sake
    copyOnInheritThreadLocal.set(newMap);
  }
}
