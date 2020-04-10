package org.muyie.framework.http;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import cn.hutool.core.util.IdUtil;

public class Request implements Serializable {

  private static final long serialVersionUID = 1L;

  private String traceId;

  public String getTraceId() {
    return StringUtils.isEmpty(traceId) ? IdUtil.fastSimpleUUID() : traceId;
  }

  public void setTraceId(String traceId) {
    this.traceId = traceId;
  }

}
