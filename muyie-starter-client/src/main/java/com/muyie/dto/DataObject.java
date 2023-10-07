package com.muyie.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DO（Data Object）：此对象与数据库表结构一一对应，通过 DAO 层向上传输数据源对象。
 *
 * @author larry.qi
 * @since 1.2.10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataObject extends DTO {

}
