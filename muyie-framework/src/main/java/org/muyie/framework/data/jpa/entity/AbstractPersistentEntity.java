package org.muyie.framework.data.jpa.entity;

import java.io.Serializable;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Base abstract class for persistent entities
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractPersistentEntity implements Serializable {

  private static final long serialVersionUID = 1L;

}
