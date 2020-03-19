package org.muyie.framework.sensitive;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 中国大陆身份证号数据脱敏过滤器
 */
public class IdcardValueFilter implements ValueFilter {

  @Override
  public Object process(Object object, String name, Object value) {
    return SensitiveDataUtil.idCardNoHide((String) value);
  }

}
