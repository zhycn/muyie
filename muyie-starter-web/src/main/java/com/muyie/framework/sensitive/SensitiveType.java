package com.muyie.framework.sensitive;

/**
 * 支持的脱敏类型枚举
 *
 * @author larry.qi
 * @since 1.2.9
 */
public enum SensitiveType {

  //中文姓名
  CHINESE_NAME,

  //身份证号
  ID_CARD,

  //手机号或电话号码
  PHONE,

  //电子邮件
  EMAIL,

  //密码
  PASSWORD,

  //中国大陆车牌，包含普通车辆、新能源车辆
  CAR_LICENSE,

  //银行卡
  BANK_CARD,

  //昵称（显示首/尾各1位，中间加**）
  NICK,

  //登录账号
  LOGON,

  //默认隐藏规则
  DEFAULT

}
