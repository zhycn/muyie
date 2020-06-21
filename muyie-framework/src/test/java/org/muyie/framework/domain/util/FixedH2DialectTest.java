package org.muyie.framework.domain.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.junit.jupiter.api.Test;
import org.muyie.framework.test.LogbackRecorder;

public class FixedH2DialectTest {

  @Test
  public void test() {
    final List<LogbackRecorder> recorders = new LinkedList<>();
    recorders.add(LogbackRecorder.forName("org.jboss.logging").reset().capture("ALL"));
    recorders.add(LogbackRecorder.forClass(Dialect.class).reset().capture("ALL"));
    recorders.add(LogbackRecorder.forClass(H2Dialect.class).reset().capture("ALL"));

    final Map<Integer, String> registered = new LinkedHashMap<>();

    new FixedH2Dialect() {

      @Override
      protected void registerColumnType(final int code, final String name) {
        registered.put(code, name);
        super.registerColumnType(code, name);
      }

    };

    assertThat(registered.get(Types.FLOAT)).isEqualTo("real");

    recorders.forEach(LogbackRecorder::release);
  }

}
