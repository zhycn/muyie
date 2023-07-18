package com.muyie.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Objects;

/**
 * BaseAuditingDO 字段 createdDate 和 lastModifiedDate 自动填充策略
 *
 * @author larry
 */
@Configuration
public class AuditingDateMetaObjectHandler implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    Date currentDate = new Date();
    Object createdDate = getFieldValByName("createdDate", metaObject);
    if (Objects.isNull(createdDate)) {
      setFieldValByName("createdDate", currentDate, metaObject);
    }
    Object lastModifiedDate = getFieldValByName("lastModifiedDate", metaObject);
    if (Objects.isNull(lastModifiedDate)) {
      setFieldValByName("lastModifiedDate", currentDate, metaObject);
    }
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    Date currentDate = new Date();
    Object lastModifiedDate = getFieldValByName("lastModifiedDate", metaObject);
    if (Objects.isNull(lastModifiedDate)) {
      setFieldValByName("lastModifiedDate", currentDate, metaObject);
    }
  }

}
