package com.muyie.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * BO（Business Object）：业务对象，可以由 Service 层输出的封装业务逻辑的对象。
 *
 * @author larry.qi
 * @since 1.2.10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessObject extends Command {

}
