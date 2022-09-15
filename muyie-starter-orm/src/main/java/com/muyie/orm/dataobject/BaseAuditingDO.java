package com.muyie.orm.dataobject;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

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
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditingDO extends BasePersistentDO {

  private static final long serialVersionUID = 1L;

  @Version
  @Column(name = "revision")
  private Integer revision;

  @CreatedBy
  @Length(max = 32)
  @Column(name = "created_by", nullable = false, length = 32, updatable = false)
  private String createdBy;

  @CreatedDate
  @Column(name = "created_date", updatable = false)
  private Date createdDate;

  @LastModifiedBy
  @Length(max = 32)
  @Column(name = "last_modified_by", length = 32)
  private String lastModifiedBy;

  @LastModifiedDate
  @Column(name = "last_modified_date")
  private Date lastModifiedDate;

  @Length(max = 512)
  @Column(name = "remark", length = 512)
  private String remark;

}
