package com.muyie.framework.sensitive;

import lombok.Data;
import lombok.NonNull;

/**
 * 敏感数据隐藏策略的字段配置。
 *
 * @author larry.qi
 * @since 1.2.9
 */
@Data
public class SensitiveStrategy {

  private SensitiveStrategy() {
  }

  /**
   * 隐藏中文姓名的字段列表（中文姓名脱敏）
   */
  private String[] chineseNames;

  /**
   * 隐藏身份证号的字段列表（身份证号脱敏）
   */
  private String[] idCards;

  /**
   * 隐藏手机号或电话号码的字段列表（手机号或电话号码脱敏）
   */
  private String[] phones;

  /**
   * 隐藏邮箱的字段列表（邮箱脱敏）
   */
  private String[] emails;

  /**
   * 隐藏密码的字段列表（密码脱敏）
   */
  private String[] passwords;

  /**
   * 隐藏车牌号的字段列表（车牌号脱敏）
   */
  private String[] carLicenses;

  /**
   * 隐藏银行卡号的字段列表（银行卡号脱敏）
   */
  private String[] bankCards;

  /**
   * 隐藏昵称的字段列表（昵称脱敏）
   */
  private String[] nicks;

  /**
   * 隐藏登录帐号的字段列表（登录帐号脱敏）
   */
  private String[] logons;

  /**
   * 默认隐藏规则的字段列表（按默认隐藏规则脱敏）
   */
  private String[] defaults;

  /**
   * 序列化时忽略的字段列表（序列化时忽略该字段）
   */
  private String[] ignores;

  public static StrategyBuilder builder() {
    return new StrategyBuilder();
  }

  public static StrategyBuilder builder(String... ignores) {
    return SensitiveStrategy.builder().ignores(ignores);
  }

  public static SensitiveStrategy of(@NonNull SensitiveConfig sensitiveConfig) {
    return builder(sensitiveConfig.ignores())
      .chineseNames(sensitiveConfig.chineseNames())
      .idCards(sensitiveConfig.idCards())
      .phones(sensitiveConfig.phones())
      .emails(sensitiveConfig.emails())
      .passwords(sensitiveConfig.passwords())
      .carLicenses(sensitiveConfig.carLicenses())
      .bankCards(sensitiveConfig.bankCards())
      .nicks(sensitiveConfig.nicks())
      .logons(sensitiveConfig.logons())
      .defaults(sensitiveConfig.defaults())
      .build();
  }

  public static class StrategyBuilder {

    private final SensitiveStrategy sensitiveStrategy = new SensitiveStrategy();

    public StrategyBuilder chineseNames(String... chineseNames) {
      sensitiveStrategy.setChineseNames(chineseNames);
      return this;
    }

    public StrategyBuilder idCards(String... idCards) {
      sensitiveStrategy.setIdCards(idCards);
      return this;
    }

    public StrategyBuilder phones(String... phones) {
      sensitiveStrategy.setPhones(phones);
      return this;
    }

    public StrategyBuilder emails(String... emails) {
      sensitiveStrategy.setEmails(emails);
      return this;
    }

    public StrategyBuilder passwords(String... passwords) {
      sensitiveStrategy.setPasswords(passwords);
      return this;
    }

    public StrategyBuilder carLicenses(String... carLicenses) {
      sensitiveStrategy.setCarLicenses(carLicenses);
      return this;
    }

    public StrategyBuilder bankCards(String... bankCards) {
      sensitiveStrategy.setBankCards(bankCards);
      return this;
    }

    public StrategyBuilder nicks(String... nicks) {
      sensitiveStrategy.setNicks(nicks);
      return this;
    }

    public StrategyBuilder logons(String... logons) {
      sensitiveStrategy.setLogons(logons);
      return this;
    }

    public StrategyBuilder defaults(String... defaults) {
      sensitiveStrategy.setDefaults(defaults);
      return this;
    }

    public StrategyBuilder ignores(String... ignores) {
      sensitiveStrategy.setIgnores(ignores);
      return this;
    }

    public SensitiveStrategy build() {
      return sensitiveStrategy;
    }
  }

}
