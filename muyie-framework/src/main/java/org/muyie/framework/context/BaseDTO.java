package org.muyie.framework.context;

import java.io.Serializable;

/**
 * 接口请求参数基类
 */
public class BaseDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 请求追踪标识
   */
  private String traceId;

  public String getTraceId() {
    return traceId;
  }

  public void setTraceId(String traceId) {
    this.traceId = traceId;
  }

}
