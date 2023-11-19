package com.muyie.mybatis;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 动态表操作工具类
 * - 1. 设置动态表名
 * - 2. 匹配动态表名规则
 * - 3. 调用任务
 */
@Data
public class DynamicTable {

  /**
   * 本地线程池记录动态表名
   */
  private static final TransmittableThreadLocal<Map<String, String>> TTL_DYNAMIC_TABLE_NAME_MAP = new TransmittableThreadLocal<>();
  private static final TransmittableThreadLocal<List<String>> TTL_DYNAMIC_TABLE_NAME_LIST = new TransmittableThreadLocal<>();

  /**
   * 动态表名集合，通过占位符替换真实表名。key=占位符，value=真实表名。
   */
  private Map<String, String> tableNameMap;

  /**
   * 动态表名集合，通过占位符替换真实表名。占位符由动态表名拦截器获取，集合中保存真实表名。
   */
  private List<String> tableNameList;

  public static DynamicTable of(Map<String, String> tableNameMap) {
    DynamicTable dt = new DynamicTable();
    dt.setTableNameMap(tableNameMap);
    return dt;
  }

  public static DynamicTable of(List<String> tableNameList) {
    DynamicTable dt = new DynamicTable();
    dt.setTableNameList(tableNameList);
    return dt;
  }

  public static DynamicTable of(String... tableNames) {
    DynamicTable dt = new DynamicTable();
    dt.setTableNameList(Arrays.asList(tableNames));
    return dt;
  }

  /**
   * Intercept table name and return matching result
   *
   * @param tableName Current table name
   * @return Replaced table name
   */
  public static String match(String tableName) {
    Map<String, String> ttlMap = TTL_DYNAMIC_TABLE_NAME_MAP.get();
    if (!CollectionUtils.isEmpty(ttlMap) && ttlMap.containsKey(tableName)) {
      return ttlMap.get(tableName);
    }
    List<String> ttlList = TTL_DYNAMIC_TABLE_NAME_LIST.get();
    if (!CollectionUtils.isEmpty(ttlList)) {
      for (String key : ttlList) {
        if (StringUtils.startsWithIgnoreCase(key, tableName)) {
          return key;
        }
      }
    }
    return tableName;
  }

  /**
   * 使用无返回结果的执行器
   *
   * @param task 任务执行器
   */
  public void submit(Runnable task) {
    try {
      TTL_DYNAMIC_TABLE_NAME_MAP.set(tableNameMap);
      TTL_DYNAMIC_TABLE_NAME_LIST.set(tableNameList);
      task.run();
    } finally {
      TTL_DYNAMIC_TABLE_NAME_MAP.remove();
      TTL_DYNAMIC_TABLE_NAME_LIST.remove();
    }
  }

  /**
   * 使用有返回结果的执行器
   *
   * @param task 任务执行器
   * @return 结果
   */
  public <T> T submit(Callable<T> task) {
    try {
      TTL_DYNAMIC_TABLE_NAME_MAP.set(tableNameMap);
      TTL_DYNAMIC_TABLE_NAME_LIST.set(tableNameList);
      return task.call();
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      TTL_DYNAMIC_TABLE_NAME_MAP.remove();
      TTL_DYNAMIC_TABLE_NAME_LIST.remove();
    }
  }

}
