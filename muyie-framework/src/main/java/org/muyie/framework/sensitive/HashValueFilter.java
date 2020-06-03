package org.muyie.framework.sensitive;

import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 基于MD5加密的HASH值
 */
public class HashValueFilter implements ValueFilter {

  @Override
  public Object process(Object object, String name, Object value) {
    return DigestUtils.md5DigestAsHex(((String) value).getBytes());
  }

}
