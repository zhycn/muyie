package com.muyie.dto;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Cmd（Command）：指令对象，用于处理增删改操作。注意超过 2 个参数的查询封装，禁止使用 Map 类来传输。
 *
 * @author larry.qi
 * @since 1.2.10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Command extends DTO {

  protected final JSONObject params = JSONObject.of();

}
