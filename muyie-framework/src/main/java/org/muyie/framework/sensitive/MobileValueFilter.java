package org.muyie.framework.sensitive;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 手机号、电话号码等数据脱敏过滤器
 */
public class MobileValueFilter implements ValueFilter {

  @Override
  public Object process(Object object, String name, Object value) {
    return SensitiveDataUtil.cellphoneHide((String) value);
  }

}
