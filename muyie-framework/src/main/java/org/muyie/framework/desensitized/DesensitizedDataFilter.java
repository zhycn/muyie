package org.muyie.framework.desensitized;

import com.alibaba.fastjson.serializer.ValueFilter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.DesensitizedUtil;

/**
 * 基于FastJSON的过滤器
 */
public class DesensitizedDataFilter implements ValueFilter {

  private static final Logger log = LoggerFactory.getLogger(DesensitizedDataFilter.class);

  // 序列化时忽略的字段名
  private final String[] ignoreFields;

  // 数据脱敏配置项
  private final DesensitizedDataConfig config;

  public DesensitizedDataFilter(@NonNull DesensitizedDataConfig config, String... ignoreFields) {
    this.ignoreFields = ignoreFields;
    this.config = config;
  }

//  @Override
//  public boolean apply(Object o, String name, Object value) {
//    return !Arrays.asList(ignoreFields).contains(name);
//  }

  @Override
  public Object process(Object o, String name, Object value) {
    if (null == value || !(value instanceof String) || ((String) value).length() == 0) {
      return value;
    }

    try {
      Assert.notNull(config); // 信息脱敏配置不能为空
      if (CollectionUtil.isNotEmpty(config.customizeHideRules()) && config.customizeHideRules().containsKey(name)) {
        return config.customizeHideRules().get(name).process(o, name, value);
      } else if (CollectionUtil.isNotEmpty(config.chineseNameRules()) && config.chineseNameRules().contains(name)) {
        return DesensitizedUtil.chineseName((String) value);
      } else if (CollectionUtil.isNotEmpty(config.idCardRules()) && config.idCardRules().contains(name)) {
        return DesensitizedUtil.idCardNum((String) value, 1, 1); // 身份证默认前1后1
      } else if (CollectionUtil.isNotEmpty(config.fixedPhoneRules()) && config.fixedPhoneRules().contains(name)) {
        return DesensitizedUtil.fixedPhone((String) value);
      } else if (CollectionUtil.isNotEmpty(config.mobilePhoneRules()) && config.mobilePhoneRules().contains(name)) {
        return DesensitizedUtil.mobilePhone((String) value);
      } else if (CollectionUtil.isNotEmpty(config.addressRules()) && config.addressRules().contains(name)) {
        int length = StringUtils.length((String) value) / 2; // 地址隐藏一半
        return DesensitizedUtil.address((String) value, length);
      } else if (CollectionUtil.isNotEmpty(config.emailRules()) && config.emailRules().contains(name)) {
        return DesensitizedUtil.email((String) value);
      } else if (CollectionUtil.isNotEmpty(config.passwordRules()) && config.passwordRules().contains(name)) {
        return DesensitizedUtil.password((String) value);
      } else if (CollectionUtil.isNotEmpty(config.carLicenseRules()) && config.carLicenseRules().contains(name)) {
        return DesensitizedUtil.carLicense((String) value);
      } else if (CollectionUtil.isNotEmpty(config.bankCardRules()) && config.bankCardRules().contains(name)) {
        return DesensitizedUtil.bankCard((String) value);
      }
    } catch (Exception e) {
      log.warn("信息脱敏处理异常：{}", e.getMessage());
    }
    return value;
  }

}
