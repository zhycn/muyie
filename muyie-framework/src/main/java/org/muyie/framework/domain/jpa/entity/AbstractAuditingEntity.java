package org.muyie.framework.domain.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Base abstract class for entities which will hold definitions for created, last modified and
 * created by date, last modified by date and remark.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity extends AbstractPersistentEntity {

  private static final long serialVersionUID = 1L;

  @CreatedBy
  @Length(max = 32)
  @Column(name = "created_by", nullable = false, length = 32, updatable = false)
  private String createdBy;

  @CreatedDate
  @Column(name = "created_date", updatable = false)
  private Date createdDate = new Date();

  @LastModifiedBy
  @Length(max = 32)
  @Column(name = "last_modified_by", length = 32)
  private String lastModifiedBy;

  @LastModifiedDate
  @Column(name = "last_modified_date")
  private Date lastModifiedDate = new Date();

  @Length(max = 512)
  @Column(name = "remark", length = 512)
  private String remark;

  public String getCreatedBy() {
    return createdBy;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public String getLastModifiedBy() {
    return lastModifiedBy;
  }

  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }

  public String getRemark() {
    return remark;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public void setLastModifiedBy(String lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public void setLastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

}
