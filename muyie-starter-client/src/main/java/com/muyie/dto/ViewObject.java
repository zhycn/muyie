package com.muyie.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * VO（View Object）：显示层对象，通常是 Web 向模板渲染引擎层传输的对象。
 *
 * @author larry.qi
 * @since 1.2.10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ViewObject extends Command {

}
