package com.muyie.mybatis.dataobject;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Base abstract class for persistent entities
 *
 * @author larry.qi
 * @since 1.2.11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseDO {

  /**
   * params 扩展参数，非数据库字段
   */
  @TableField(exist = false)
  protected final JSONObject params = JSONObject.of();

  public JSONObject getParams() {
    return params;
  }

  public void setParams(Map<String, Object> params) {
    getParams().putAll(params);
  }

}
