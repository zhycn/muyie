package com.muyie.framework.sensitive;

import com.alibaba.fastjson2.filter.BeforeFilter;
import com.alibaba.fastjson2.filter.ValueFilter;

import java.util.Objects;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * JSON序列化时数据脱敏策略
 *
 * @author larry.qi
 * @since 1.2.9
 */
@Slf4j
public class SensitiveValueFilter extends BeforeFilter implements ValueFilter {

  private SensitiveStrategy sensitiveStrategy;

  public SensitiveValueFilter(SensitiveStrategy sensitiveStrategy) {
    this.sensitiveStrategy = sensitiveStrategy;
  }

  @Override
  public Object apply(Object object, String name, Object value) {
    if (!(value instanceof String) || ((String) value).length() == 0) {
      return value;
    }
    if (Objects.isNull(sensitiveStrategy)) {
      return value;
    }
    if (ArrayUtil.contains(sensitiveStrategy.getChineseNames(), name)) {
      //中文姓名
      return SensitiveUtil.chineseNameHide((String) value);
    } else if (ArrayUtil.contains(sensitiveStrategy.getIdCards(), name)) {
      //身份证号
      return SensitiveUtil.idCardHide((String) value);
    } else if (ArrayUtil.contains(sensitiveStrategy.getPhones(), name)) {
      //手机号或电话号码
      return SensitiveUtil.phoneHide((String) value);
    } else if (ArrayUtil.contains(sensitiveStrategy.getEmails(), name)) {
      //电子邮件
      return SensitiveUtil.emailHide((String) value);
    } else if (ArrayUtil.contains(sensitiveStrategy.getPasswords(), name)) {
      //密码
      return SensitiveUtil.passwordHide((String) value);
    } else if (ArrayUtil.contains(sensitiveStrategy.getCarLicenses(), name)) {
      //中国大陆车牌，包含普通车辆、新能源车辆
      return SensitiveUtil.carLicenseHide((String) value);
    } else if (ArrayUtil.contains(sensitiveStrategy.getBankCards(), name)) {
      //银行卡
      return SensitiveUtil.bankCardHide((String) value);
    } else if (ArrayUtil.contains(sensitiveStrategy.getNicks(), name)) {
      //昵称（显示首/尾各1位，中间加**）
      return SensitiveUtil.nickHide((String) value);
    } else if (ArrayUtil.contains(sensitiveStrategy.getLogons(), name)) {
      //登录账号
      return SensitiveUtil.logonHide((String) value);
    } else if (ArrayUtil.contains(sensitiveStrategy.getDefaults(), name)) {
      //默认隐藏规则
      return SensitiveUtil.defaultHide((String) value);
    }
    return value;
  }

  public static SensitiveValueFilter of(SensitiveStrategy sensitiveStrategy) {
    return new SensitiveValueFilter(sensitiveStrategy);
  }

  @Override
  public void writeBefore(Object object) {
    SensitiveConfig sensitiveConfig = object.getClass().getAnnotation(SensitiveConfig.class);
    if (sensitiveConfig != null) {
      this.sensitiveStrategy = SensitiveStrategy.of(sensitiveConfig);
    }
  }

}
