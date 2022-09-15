package com.muyie.orm.dataobject;

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

  private Integer revision;

  @Length(max = 32)
  private String createdBy;

  private Date createdDate;

  @Length(max = 32)
  private String lastModifiedBy;

  private Date lastModifiedDate;

  @Length(max = 512)
  private String remark;

}
