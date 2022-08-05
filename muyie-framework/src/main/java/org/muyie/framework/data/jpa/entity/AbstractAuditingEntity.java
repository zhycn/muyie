package org.muyie.framework.data.jpa.entity;

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

/**
 * Base abstract class for entities which will hold definitions for created, last modified and
 * created by date, last modified by date and remark.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity extends AbstractPersistentEntity {

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

  public void setCreatedBy(final String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(final Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(final String lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(final Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(final String remark) {
    this.remark = remark;
  }

  public Integer getRevision() {
    return revision;
  }

  public void setRevision(final Integer revision) {
    this.revision = revision;
  }

}
