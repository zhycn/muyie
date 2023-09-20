package com.muyie.mybatis.dataobject;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通用实体基类
 *
 * @author larry.qi
 * @since 2.7.13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseDO {

  /**
   * params 扩展参数，非数据库字段
   */
  @TableField(exist = false)
  protected final JSONObject params = JSONObject.of();

}
