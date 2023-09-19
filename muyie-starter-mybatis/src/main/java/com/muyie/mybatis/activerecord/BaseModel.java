package com.muyie.mybatis.activerecord;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 通用实体基类（ActiveRecord模式）
 *
 * @author larry
 * @since 2.7.13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseModel<T extends Model<?>> extends Model<T> {

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
