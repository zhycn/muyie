package org.muyie.framework.domain.service.filter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DurationFilterTest {

  private DurationFilter filter;

  private final Duration value = Duration.ofMinutes(5);

  @BeforeEach
  public void setup() {
    filter = new DurationFilter();
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
    assertThat(filter.toString()).isEqualTo("DurationFilter []");
  }

  @Test
  public void testSetEquals() {
    final Filter<Duration> chain = filter.setEquals(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getEquals()).isEqualTo(value);
  }

  @Test
  public void testSetLessThan() {
    final Filter<Duration> chain = filter.setLessThan(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getLessThan()).isEqualTo(value);
  }

  @Test
  public void testSetLessThanOrEqual() {
    final Filter<Duration> chain = filter.setLessThanOrEqual(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getLessThanOrEqual()).isEqualTo(value);
  }

  @Test
  public void testSetGreaterThan() {
    final Filter<Duration> chain = filter.setGreaterThan(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getGreaterThan()).isEqualTo(value);
  }

  @Test
  public void testSetGreaterThanOrEqual() {
    final Filter<Duration> chain = filter.setGreaterThanOrEqual(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getGreaterThanOrEqual()).isEqualTo(value);
  }

  @Test
  public void testSetSpecified() {
    final Filter<Duration> chain = filter.setSpecified(true);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getSpecified()).isEqualTo(true);
  }

  @Test
  public void testSetIn() {
    final List<Duration> list = new LinkedList<>();
    final Filter<Duration> chain = filter.setIn(list);
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
    assertThat(filter.toString()).isEqualTo("DurationFilter " + "[greaterThan=" + str + ", greaterThanOrEqual=" + str
        + ", lessThan=" + str + ", " + "lessThanOrEqual=" + str + ", equals=" + str + ", specified=true, in=[]]");
  }
}
