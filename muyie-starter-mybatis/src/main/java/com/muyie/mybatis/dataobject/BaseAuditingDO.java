package com.muyie.mybatis.dataobject;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.Version;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Base abstract class for entities which will hold definitions for created, last modified and
 * created by date, last modified by date and remark.
 *
 * @author larry.qi
 * @since 1.2.11
 */
@Getter
@Setter
public abstract class BaseAuditingDO extends BasePersistentDO {

  private static final long serialVersionUID = 1L;

  /**
   * 模糊查询参数
   */
  private String searchValue;

  /**
   * 修订版本号
   */
  @Version
  private Integer revision;

  @Length(max = 32)
  private String createdBy;

  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date createdDate;

  @Length(max = 32)
  private String lastModifiedBy;

  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date lastModifiedDate;

  @Length(max = 512)
  private String remark;

}
