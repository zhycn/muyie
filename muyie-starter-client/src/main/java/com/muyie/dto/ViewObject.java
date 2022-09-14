package com.muyie.dto;

import com.alibaba.fastjson2.JSONObject;

import java.util.Map;

/**
 * VO (View Object) - 显示层对象，通常是 Web 向模板渲染引擎层传输的对象。
 *
 * @author larry.qi
 * @since 1.2.10
 */
public class ViewObject extends DTO {
  private static final long serialVersionUID = 1L;

  protected final JSONObject params = new JSONObject();

  public JSONObject getParams() {
    return params;
  }

  public void setParams(Map<String, Object> params) {
    getParams().putAll(params);
  }

}
