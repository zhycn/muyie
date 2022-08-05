package org.muyie.framework.desensitized;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.alibaba.fastjson.serializer.ValueFilter;

import java.util.List;
import java.util.Map;

/**
 * 信息脱敏配置接口，目前提供9种规则+自定义信息脱敏规则。参考：{@link cn.hutool.core.util.DesensitizedUtil}
 * <p>
 * 中文姓名 | 身份证号 | 座机号 | 手机号 | 地址 | 电子邮件 | 密码 | 中国大陆车牌，包含普通车辆、新能源车辆 | 银行卡
 */
public interface DesensitizedDataConfig {

  List<String> EMPTY_LIST = Lists.newArrayList();
  Map<String, ValueFilter> EMPTY_MAP = Maps.newConcurrentMap();

  // 自定义信息脱敏规则（高优先级）- 属性 + 过滤器
  default Map<String, ValueFilter> customizeHideRules() {
    return EMPTY_MAP;
  }

  // 中文姓名
  default List<String> chineseNameRules() {
    return EMPTY_LIST;
  }

  // 身份证号
  default List<String> idCardRules() {
    return EMPTY_LIST;
  }

  // 座机号
  default List<String> fixedPhoneRules() {
    return EMPTY_LIST;
  }

  // 手机号
  default List<String> mobilePhoneRules() {
    return EMPTY_LIST;
  }

  // 地址
  default List<String> addressRules() {
    return EMPTY_LIST;
  }

  // 电子邮件
  default List<String> emailRules() {
    return EMPTY_LIST;
  }

  // 密码
  default List<String> passwordRules() {
    return EMPTY_LIST;
  }

  // 中国大陆车牌，包含普通车辆、新能源车辆
  default List<String> carLicenseRules() {
    return EMPTY_LIST;
  }

  // 银行卡
  default List<String> bankCardRules() {
    return EMPTY_LIST;
  }

}
