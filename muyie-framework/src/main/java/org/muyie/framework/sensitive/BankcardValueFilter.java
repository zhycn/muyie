package org.muyie.framework.sensitive;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 银行卡号数据脱敏过滤器
 */
public class BankcardValueFilter implements ValueFilter {

  @Override
  public Object process(Object object, String name, Object value) {
    return SensitiveDataUtil.bankCardNoHide((String) value);
  }

}
