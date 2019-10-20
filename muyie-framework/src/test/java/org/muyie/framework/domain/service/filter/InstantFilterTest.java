package org.muyie.framework.domain.service.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.muyie.framework.domain.service.filter.Filter;
import org.muyie.framework.domain.service.filter.InstantFilter;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InstantFilterTest {

  private InstantFilter filter;

  private Instant value = Instant.now();

  @BeforeEach
  public void setup() {
    filter = new InstantFilter();
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
    assertThat(filter.toString()).isEqualTo("InstantFilter []");
  }

  @Test
  public void testSetEquals() {
    Filter<Instant> chain = filter.setEquals(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getEquals()).isEqualTo(value);
  }

  @Test
  public void testSetLessThan() {
    Filter<Instant> chain = filter.setLessThan(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getLessThan()).isEqualTo(value);
  }

  @Test
  public void testSetLessThanOrEqual() {
    Filter<Instant> chain = filter.setLessThanOrEqual(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getLessThanOrEqual()).isEqualTo(value);
  }

  @Test
  public void testSetGreaterThan() {
    Filter<Instant> chain = filter.setGreaterThan(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getGreaterThan()).isEqualTo(value);
  }

  @Test
  public void testSetGreaterThanOrEqual() {
    Filter<Instant> chain = filter.setGreaterThanOrEqual(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getGreaterThanOrEqual()).isEqualTo(value);
  }

  @Test
  public void testSetSpecified() {
    Filter<Instant> chain = filter.setSpecified(true);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getSpecified()).isEqualTo(true);
  }

  @Test
  public void testSetIn() {
    List<Instant> list = new LinkedList<>();
    Filter<Instant> chain = filter.setIn(list);
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
    String str = value.toString();
    assertThat(filter.toString()).isEqualTo("InstantFilter " + "[greaterThan=" + str
        + ", greaterThanOrEqual=" + str + ", lessThan=" + str + ", " + "lessThanOrEqual=" + str
        + ", equals=" + str + ", specified=true, in=[]]");
  }
}
