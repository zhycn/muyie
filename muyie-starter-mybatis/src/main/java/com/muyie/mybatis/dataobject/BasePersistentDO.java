package com.muyie.mybatis.dataobject;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;

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

  @TableField(insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
  protected final JSONObject params = JSONObject.of();

  public JSONObject getParams() {
    return params;
  }

  public void setParams(Map<String, Object> params) {
    getParams().putAll(params);
  }

}
