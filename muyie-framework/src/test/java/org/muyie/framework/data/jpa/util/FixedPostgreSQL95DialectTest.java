package org.muyie.framework.data.jpa.util;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.descriptor.sql.BinaryTypeDescriptor;
import org.hibernate.type.descriptor.sql.BlobTypeDescriptor;
import org.hibernate.type.descriptor.sql.BooleanTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.muyie.framework.test.LogbackRecorder;

import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class FixedPostgreSQL95DialectTest {

  private final List<LogbackRecorder> recorders = new LinkedList<>();

  private final Map<Integer, String> registered = new LinkedHashMap<>();

  private FixedPostgreSQL95Dialect dialect;

  @BeforeEach
  public void setup() {
    recorders.add(LogbackRecorder.forName("org.jboss.logging").reset().capture("ALL"));
    recorders.add(LogbackRecorder.forClass(Dialect.class).reset().capture("ALL"));

    dialect = new FixedPostgreSQL95Dialect() {

      @Override
      protected void registerColumnType(final int code, final String name) {
        registered.put(code, name);
        super.registerColumnType(code, name);
      }

    };
  }

  @AfterEach
  public void teardown() {
    recorders.forEach(LogbackRecorder::release);
    recorders.clear();
    registered.clear();
  }

  @Test
  public void testBlobTypeRegister() {
    assertThat(registered.get(Types.BLOB)).isEqualTo("bytea");
  }

  @Test
  public void testBlobTypeRemap() {
    final SqlTypeDescriptor descriptor = dialect.remapSqlTypeDescriptor(BlobTypeDescriptor.DEFAULT);
    assertThat(descriptor).isEqualTo(BinaryTypeDescriptor.INSTANCE);
  }

  @Test
  public void testOtherTypeRemap() {
    final SqlTypeDescriptor descriptor = dialect.remapSqlTypeDescriptor(BooleanTypeDescriptor.INSTANCE);
    assertThat(descriptor).isEqualTo(BooleanTypeDescriptor.INSTANCE);
  }
}
