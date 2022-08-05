package org.muyie.framework.desensitized;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import org.muyie.framework.config.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;

import cn.hutool.core.util.DesensitizedUtil;

/**
 * 增强版信息脱敏工具类
 */
public class DesensitizedDataUtil extends DesensitizedUtil {

  public final static DesensitizedDataConfig DEFAULT_CONFIG = new DesensitizedDataConfig() {
  };
  private final static Logger log = LoggerFactory.getLogger(DesensitizedDataUtil.class);

  /**
   * 对象转成信息脱敏后的JSON对象
   *
   * @param object 序列化对象
   * @return 信息脱敏后的JSON字符串
   */
  public static String toJson(final Object object) {
    DesensitizedDataConfig config = DEFAULT_CONFIG;
    try {
      config = SpringContextHolder.getApplicationContext().getBean(DesensitizedDataConfig.class);
    } catch (Exception e) {
      log.warn("信息脱敏规则对象【DesensitizedDataConfig】未配置");
    }
    return toJson(object, config);
  }

  /**
   * 对象转成信息脱敏后的JSON对象
   *
   * @param object       序列化对象
   * @param ignoreFields 序列化时忽略的字段
   * @return 信息脱敏后的JSON字符串
   */
  public static String toJson(final Object object, String... ignoreFields) {
    DesensitizedDataConfig config = DEFAULT_CONFIG;
    try {
      config = SpringContextHolder.getApplicationContext().getBean(DesensitizedDataConfig.class);
    } catch (Exception e) {
      log.warn("信息脱敏规则对象【DesensitizedDataConfig】未配置");
    }
    return toJson(object, config, ignoreFields);
  }

  /**
   * 对象转成信息脱敏后的JSON对象
   *
   * @param object       序列化对象
   * @param requiredType SpringBean Class
   * @param <T>          DesensitizedDataConfig
   * @return 信息脱敏后的JSON字符串
   */
  public static <T extends DesensitizedDataConfig> String toJson(final Object object, Class<T> requiredType) {
    DesensitizedDataConfig config = SpringContextHolder.getApplicationContext().getBean(requiredType);
    return JSON.toJSONString(object, newBuilder(config, ""), SerializerFeature.WriteDateUseDateFormat);
  }

  /**
   * 对象转成信息脱敏后的JSON对象
   *
   * @param object       序列化对象
   * @param requiredType SpringBean Class
   * @param ignoreFields 序列化时忽略的字段
   * @param <T>          DesensitizedDataConfig
   * @return 信息脱敏后的JSON字符串
   */
  public static <T extends DesensitizedDataConfig> String toJson(final Object object, Class<T> requiredType, String... ignoreFields) {
    DesensitizedDataConfig config = SpringContextHolder.getApplicationContext().getBean(requiredType);
    return JSON.toJSONString(object, newBuilder(config, ignoreFields), SerializerFeature.WriteDateUseDateFormat);
  }

  /**
   * 对象转成信息脱敏后的JSON对象
   *
   * @param object   序列化对象
   * @param beanName SpringBean名称，DesensitizedDataConfig接口的子类
   * @return 信息脱敏后的JSON字符串
   */
  public static String toJson(String beanName, final Object object) {
    DesensitizedDataConfig config = SpringContextHolder.getApplicationContext().getBean(beanName, DesensitizedDataConfig.class);
    return JSON.toJSONString(object, newBuilder(config, ""), SerializerFeature.WriteDateUseDateFormat);
  }

  /**
   * 对象转成信息脱敏后的JSON对象
   *
   * @param object       序列化对象
   * @param beanName     SpringBean名称，DesensitizedDataConfig接口的子类
   * @param ignoreFields 序列化时忽略的字段
   * @return 信息脱敏后的JSON字符串
   */
  public static String toJson(String beanName, final Object object, String... ignoreFields) {
    DesensitizedDataConfig config = SpringContextHolder.getApplicationContext().getBean(beanName, DesensitizedDataConfig.class);
    return JSON.toJSONString(object, newBuilder(config, ignoreFields), SerializerFeature.WriteDateUseDateFormat);
  }

  /**
   * 对象转成信息脱敏后的JSON对象
   *
   * @param object 序列化对象
   * @param config 信息脱敏配置规则
   * @return 信息脱敏后的JSON字符串
   */
  public static String toJson(final Object object, @NotNull DesensitizedDataConfig config) {
    return JSON.toJSONString(object, newBuilder(config, ""), SerializerFeature.WriteDateUseDateFormat);
  }

  /**
   * 对象转成信息脱敏后的JSON对象
   *
   * @param object       序列化对象
   * @param config       信息脱敏配置规则
   * @param ignoreFields 序列化时忽略的字段
   * @return 信息脱敏后的JSON字符串
   */
  public static String toJson(final Object object, @NotNull DesensitizedDataConfig config, String... ignoreFields) {
    return JSON.toJSONString(object, newBuilder(config, ignoreFields), SerializerFeature.WriteDateUseDateFormat);
  }

  /**
   * 构建一个DesensitizedDataFilter过滤器
   *
   * @param config       配置对象
   * @param ignoreFields 序列化时忽略的字段
   * @return 过滤器
   */
  private static DesensitizedDataFilter newBuilder(@NotNull DesensitizedDataConfig config, String... ignoreFields) {
    return new DesensitizedDataFilter(config, ignoreFields);
  }

}
