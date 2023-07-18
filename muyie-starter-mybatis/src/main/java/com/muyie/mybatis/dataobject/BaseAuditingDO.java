package com.muyie.mybatis.dataobject;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Base abstract class for entities which will hold definitions for created, last modified and
 * created by date, last modified by date and remark.
 *
 * @author larry.qi
 * @since 1.2.11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseAuditingDO extends BasePersistentDO {

  /**
   * 修订版本号
   */
  @Version
  private Integer revision;

  /**
   * 创建人
   */
  @Length(max = 32)
  @TableField(updateStrategy = FieldStrategy.NEVER)
  private String createdBy;

  /**
   * 创建时间
   */
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  @TableField(updateStrategy = FieldStrategy.NEVER)
  private Date createdDate;

  /**
   * 更新人
   */
  @Length(max = 32)
  private String lastModifiedBy;

  /**
   * 更新时间
   */
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date lastModifiedDate;

  /**
   * 备注信息
   */
  @Length(max = 512)
  private String remark;

}
