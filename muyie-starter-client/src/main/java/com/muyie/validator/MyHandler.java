package com.muyie.validator;

import org.springframework.lang.NonNull;

/**
 * 对象校验处理器
 *
 * @author larry
 * @since 2.7.14
 */
@FunctionalInterface
public interface MyHandler {

  /**
   * 校验值是否有效
   *
   * @param value 参数值
   * @return true=校验通过 false=校验失败
   */
  boolean isValid(@NonNull Object value);

}
