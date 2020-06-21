package org.muyie.framework.domain.service.filter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntegerFilterTest {

  private IntegerFilter filter;

  private final Integer value = 42;

  @BeforeEach
  public void setup() {
    filter = new IntegerFilter();
  }

  @Test
  public void testConstructor() {
    assertThat(filter.getEquals()).isNull();
    assertThat(filter.getGreaterThan()).isNull();
    assertThat(filter.getGreaterThanOrEqual()).isNull();
    assertThat(filter.getLessThan()).isNull();
    assertThat(filter.getLessThanOrEqual()).isNull();
    assertThat(filter.getSpecified()).isNull();
    assertThat(filter.getIn()).isNull();
    assertThat(filter.toString()).isEqualTo("IntegerFilter []");
  }

  @Test
  public void testSetEquals() {
    final Filter<Integer> chain = filter.setEquals(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getEquals()).isEqualTo(value);
  }

  @Test
  public void testSetLessThan() {
    final Filter<Integer> chain = filter.setLessThan(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getLessThan()).isEqualTo(value);
  }

  @Test
  public void testSetLessThanOrEqual() {
    final Filter<Integer> chain = filter.setLessThanOrEqual(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getLessThanOrEqual()).isEqualTo(value);
  }

  @Test
  public void testSetGreaterThan() {
    final Filter<Integer> chain = filter.setGreaterThan(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getGreaterThan()).isEqualTo(value);
  }

  @Test
  public void testSetGreaterThanOrEqual() {
    final Filter<Integer> chain = filter.setGreaterThanOrEqual(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getGreaterThanOrEqual()).isEqualTo(value);
  }

  @Test
  public void testSetSpecified() {
    final Filter<Integer> chain = filter.setSpecified(true);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getSpecified()).isEqualTo(true);
  }

  @Test
  public void testSetIn() {
    final List<Integer> list = new LinkedList<>();
    final Filter<Integer> chain = filter.setIn(list);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getIn()).isEqualTo(list);
  }

  @Test
  public void testToString() {
    filter.setEquals(value);
    filter.setLessThan(value);
    filter.setLessThanOrEqual(value);
    filter.setGreaterThan(value);
    filter.setGreaterThanOrEqual(value);
    filter.setSpecified(true);
    filter.setIn(new LinkedList<>());
    final String str = value.toString();
    assertThat(filter.toString()).isEqualTo("IntegerFilter " + "[greaterThan=" + str + ", greaterThanOrEqual=" + str
        + ", lessThan=" + str + ", " + "lessThanOrEqual=" + str + ", equals=" + str + ", specified=true, in=[]]");
  }
}
