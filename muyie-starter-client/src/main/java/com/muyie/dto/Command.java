package com.muyie.dto;

import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

import java.util.Map;

/**
 * Cmd（Command）：指令对象，用于处理增删改操作。
 *
 * @author larry.qi
 * @since 1.2.10
 */
@Getter
public class Command extends DTO {

  private static final long serialVersionUID = 1L;

  protected final JSONObject params = JSONObject.of();

}
