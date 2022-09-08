package com.muyie.framework.sensitive;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 敏感数据隐藏策略的字段配置。
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
   * 隐藏中文姓名的字段列表（中文姓名脱敏）
   */
  String[] chineseNames() default {};

  /**
   * 隐藏身份证号的字段列表（身份证号脱敏）
   */
  String[] idCards() default {};

  /**
   * 隐藏手机号或电话号码的字段列表（手机号或电话号码脱敏）
   */
  String[] phones() default {};

  /**
   * 隐藏邮箱的字段列表（邮箱脱敏）
   */
  String[] emails() default {};

  /**
   * 隐藏密码的字段列表（密码脱敏）
   */
  String[] passwords() default {};

  /**
   * 隐藏车牌号的字段列表（车牌号脱敏）
   */
  String[] carLicenses() default {};

  /**
   * 隐藏银行卡号的字段列表（银行卡号脱敏）
   */
  String[] bankCards() default {};

  /**
   * 隐藏昵称的字段列表（昵称脱敏）
   */
  String[] nicks() default {};

  /**
   * 隐藏登录帐号的字段列表（登录帐号脱敏）
   */
  String[] logons() default {};

  /**
   * 默认隐藏规则的字段列表（按默认规则脱敏）
   */
  String[] defaults() default {};

  /**
   * 序列化时忽略的字段列表（序列化时忽略该字段）
   */
  String[] ignores() default {};

}
