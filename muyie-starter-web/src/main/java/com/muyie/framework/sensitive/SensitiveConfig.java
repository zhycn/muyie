package com.muyie.framework.sensitive;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 脱敏策略的字段配置
 *
 * @author larry.qi
 * @see SensitiveStrategy
 * @since 1.2.9
 */
@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveConfig {

  /**
   * 隐藏中文名称的字段列表
   */
  String[] chineseNames() default {};

  /**
   * 隐藏身份证号的字段列表
   */
  String[] idCards() default {};

  /**
   * 隐藏手机号或电话号码的字段列表
   */
  String[] phones() default {};

  /**
   * 隐藏邮箱的字段列表
   */
  String[] emails() default {};

  /**
   * 隐藏密码的字段列表
   */
  String[] passwords() default {};

  /**
   * 隐藏车牌号的字段列表
   */
  String[] carLicenses() default {};

  /**
   * 隐藏银行卡号的字段列表
   */
  String[] bankCards() default {};

  /**
   * 隐藏昵称的字段列表
   */
  String[] nicks() default {};

  /**
   * 隐藏登录帐号的字段列表
   */
  String[] logons() default {};

  /**
   * 默认隐藏规则的字段列表
   */
  String[] defaults() default {};

  /**
   * 序列化时忽略的字段列表
   */
  String[] ignores() default {};

}
