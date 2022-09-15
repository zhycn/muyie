package com.muyie.orm.jpa.util;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.type.descriptor.sql.BinaryTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

import java.sql.Types;

/**
 * <p>
 * FixedPostgreSQL95Dialect class.
 * </p>
 */
public class FixedPostgreSQL95Dialect extends PostgreSQL95Dialect {

  /**
   * <p>
   * Constructor for FixedPostgreSQL95Dialect.
   * </p>
   */
  public FixedPostgreSQL95Dialect() {
    super();
    registerColumnType(Types.BLOB, "bytea");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SqlTypeDescriptor remapSqlTypeDescriptor(SqlTypeDescriptor sqlTypeDescriptor) {
    if (sqlTypeDescriptor.getSqlType() == Types.BLOB) {
      return BinaryTypeDescriptor.INSTANCE;
    }
    return super.remapSqlTypeDescriptor(sqlTypeDescriptor);
  }
}
