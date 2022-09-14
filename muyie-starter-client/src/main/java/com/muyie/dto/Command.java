package com.muyie.dto;

import com.alibaba.fastjson2.JSONObject;

import java.util.Map;

/**
 * Cmd (Command) - 指令对象，用于处理增删改操作。
 *
 * @author larry.qi
 * @since 1.2.10
 */
public class Command extends DTO {

  private static final long serialVersionUID = 1L;

  protected final JSONObject params = new JSONObject();

  public JSONObject getParams() {
    return params;
  }

  public void setParams(Map<String, Object> params) {
    getParams().putAll(params);
  }

}
