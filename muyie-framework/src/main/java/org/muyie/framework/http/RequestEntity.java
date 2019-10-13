package org.muyie.framework.http;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.util.StringUtils;

import cn.hutool.core.util.IdUtil;

/**
 * 通用请求实体
 */
public class RequestEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  private String traceId;

  private final Map<String, Object> bizContent = new HashMap<>();

  public void addBizContent(String key, Object value) {
    this.bizContent.put(key, value);
  }

  public Map<String, Object> getBizContent() {
    return bizContent;
  }

  public String getTraceId() {
    return StringUtils.isEmpty(traceId) ? IdUtil.fastSimpleUUID() : traceId;
  }

  public void setBizContent(Map<String, Object> bizContent) {
    if (Objects.nonNull(bizContent)) {
      this.bizContent.putAll(bizContent);
    }
  }

  public void setTraceId(String traceId) {
    this.traceId = traceId;
  }

}
