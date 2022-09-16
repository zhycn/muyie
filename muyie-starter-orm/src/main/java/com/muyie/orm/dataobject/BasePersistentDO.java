package com.muyie.orm.dataobject;

import com.alibaba.fastjson2.JSONObject;

import java.io.Serializable;
import java.util.Map;

/**
 * Base abstract class for persistent entities
 *
 * @author larry.qi
 * @since 1.2.11
 */
public abstract class BasePersistentDO implements Serializable {

  private static final long serialVersionUID = 1L;

  protected final JSONObject params = new JSONObject();

  public JSONObject getParams() {
    return params;
  }

  public void setParams(Map<String, Object> params) {
    getParams().putAll(params);
  }

}
