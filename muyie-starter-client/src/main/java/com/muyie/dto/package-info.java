/**
 * 分层领域模型规约：
 * <pre>
 * - DO（Data Object）：此对象与数据库表结构一一对应，通过 DAO 层向上传输数据源对象。
 * - DTO（Data Transfer Object）：数据传输对象，Service 或 Manager 向外传输的对象。
 * - BO（Business Object）：业务对象，可以由 Service 层输出的封装业务逻辑的对象。
 * - Query：数据查询对象，各层接收上层的查询请求。注意超过 2 个参数的查询封装，禁止使用 Map 类来传输。
 * - VO（View Object）：显示层对象，通常是 Web 向模板渲染引擎层传输的对象。
 * - Cmd（Command）：指令对象，用于处理增删改操作。注意超过 2 个参数的查询封装，禁止使用 Map 类来传输。
 * - PageQuery：分页查询对象，内置了几个常用参数。
 * </pre>
 * <p>
 * 接口响应规约：
 * <pre>
 * - Response：基础响应对象
 * - SingleResponse：返回单个对象
 * - MultiResponse：返回多个对象
 * - PageResponse：分页查询时，返回多个对象和总记录数
 * - ApiResponse：通用响应对象
 * </pre>
 */
package com.muyie.dto;
