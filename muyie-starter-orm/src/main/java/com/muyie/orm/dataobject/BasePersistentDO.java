package com.muyie.orm.dataobject;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * Base abstract class for persistent entities
 *
 * @author larry.qi
 * @since 1.2.11
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BasePersistentDO implements Serializable {

  private static final long serialVersionUID = 1L;

}
