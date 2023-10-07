package com.muyie.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Query：数据查询对象，各层接收上层的查询请求。注意超过 2 个参数的查询封装，禁止使用 Map 类来传输。
 *
 * @author larry.qi
 * @since 1.2.10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Query extends Command {

}
