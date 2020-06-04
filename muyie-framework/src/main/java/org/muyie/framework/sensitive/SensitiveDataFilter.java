package org.muyie.framework.sensitive;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.ValueFilter;

import cn.hutool.core.util.ReflectUtil;

public class SensitiveDataFilter implements ValueFilter, PropertyFilter {

  private final String[] ignoreFields;
  private final NameValueFilter nameValueFilter;
  private final EmailValueFilter emailValueFilter;
  private final MobileValueFilter mobileValueFilter;
  private final IdcardValueFilter idcardValueFilter;
  private final BankcardValueFilter bankcardValueFilter;
  private final PasswordValueFilter passwordValueFilter;
  private final HashValueFilter hashValueFilter;

  public SensitiveDataFilter(String... ignoreFields) {
    this.ignoreFields = ignoreFields;
    this.nameValueFilter = new NameValueFilter();
    this.emailValueFilter = new EmailValueFilter();
    this.mobileValueFilter = new MobileValueFilter();
    this.idcardValueFilter = new IdcardValueFilter();
    this.bankcardValueFilter = new BankcardValueFilter();
    this.passwordValueFilter = new PasswordValueFilter();
    this.hashValueFilter = new HashValueFilter();
  }

  @Override
  public Object process(Object object, String name, Object value) {
    if (null == value || !(value instanceof String) || ((String) value).length() == 0) {
      return value;
    }

    try {
      Field field = object.getClass().getDeclaredField(name);
      Sensitive sensitive = field.getAnnotation(Sensitive.class);

      if (String.class != field.getType() || Objects.isNull(sensitive)) {
        return value;
      }

      if (sensitive.value() == SensitiveType.CUSTOMIZE_HIDE) {
        ValueFilter valueFilter = ReflectUtil.newInstance(sensitive.filter());
        return valueFilter.process(object, name, value);
      } else if (sensitive.value() == SensitiveType.NAME_HIDE) {
        return nameValueFilter.process(object, name, value);
      } else if (sensitive.value() == SensitiveType.MOBILE_HIDE) {
        return mobileValueFilter.process(object, name, value);
      } else if (sensitive.value() == SensitiveType.EMAIL_HIDE) {
        return emailValueFilter.process(object, name, value);
      } else if (sensitive.value() == SensitiveType.BANKCARD_HIDE) {
        return bankcardValueFilter.process(object, name, value);
      } else if (sensitive.value() == SensitiveType.IDCARD_HIDE) {
        return idcardValueFilter.process(object, name, value);
      } else if (sensitive.value() == SensitiveType.PASSWORD_HIDE) {
        return passwordValueFilter.process(object, name, value);
      } else if (sensitive.value() == SensitiveType.HASH_HIDE) {
        return hashValueFilter.process(object, name, value);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return value;
  }

  @Override
  public boolean apply(Object object, String name, Object value) {
    return !Arrays.asList(ignoreFields).contains(name);
  }

}
