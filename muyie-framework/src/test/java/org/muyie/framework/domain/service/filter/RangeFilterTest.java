package org.muyie.framework.domain.service.filter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RangeFilterTest {

  private RangeFilter<Short> filter;

  private final Short value = 42;

  @BeforeEach
  public void setup() {
    filter = new RangeFilter<Short>();
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
    assertThat(filter.toString()).isEqualTo("RangeFilter []");
  }

  @Test
  public void testCopy() {
    final RangeFilter<Short> copy = filter.copy();
    assertThat(copy).isNotSameAs(filter);
    assertThat(copy.getEquals()).isNull();
    assertThat(copy.getGreaterThan()).isNull();
    assertThat(copy.getGreaterThanOrEqual()).isNull();
    assertThat(copy.getLessThan()).isNull();
    assertThat(copy.getLessThanOrEqual()).isNull();
    assertThat(copy.getSpecified()).isNull();
    assertThat(copy.getIn()).isNull();
    assertThat(copy.toString()).isEqualTo("RangeFilter []");
  }

  @Test
  public void testSetEquals() {
    final Filter<Short> chain = filter.setEquals(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getEquals()).isEqualTo(value);
  }

  @Test
  public void testSetLessThan() {
    final Filter<Short> chain = filter.setLessThan(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getLessThan()).isEqualTo(value);
  }

  @Test
  public void testSetLessThanOrEqual() {
    final Filter<Short> chain = filter.setLessThanOrEqual(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getLessThanOrEqual()).isEqualTo(value);
  }

  @Test
  public void testSetGreaterThan() {
    final Filter<Short> chain = filter.setGreaterThan(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getGreaterThan()).isEqualTo(value);
  }

  @Test
  public void testSetGreaterThanOrEqual() {
    final Filter<Short> chain = filter.setGreaterThanOrEqual(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getGreaterThanOrEqual()).isEqualTo(value);
  }

  @Test
  public void testSetSpecified() {
    final Filter<Short> chain = filter.setSpecified(true);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getSpecified()).isEqualTo(true);
  }

  @Test
  public void testSetIn() {
    final List<Short> list = new LinkedList<>();
    final Filter<Short> chain = filter.setIn(list);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getIn()).isEqualTo(list);
  }

  @Test
  public void testEquals() {
    final RangeFilter<Short> filter2 = new RangeFilter<>();
    assertThat(filter).isEqualTo(filter2);
    filter.setEquals(value);
    filter2.setEquals(value);
    assertThat(filter).isEqualTo(filter2);
    filter.setIn(Lists.newArrayList(value, value));
    filter2.setIn(Lists.newArrayList(value, value));
    assertThat(filter).isEqualTo(filter2);
    filter.setLessThan(value);
    filter2.setLessThan(value);
    assertThat(filter).isEqualTo(filter2);
    filter.setGreaterThanOrEqual(value);
    filter2.setGreaterThanOrEqual(value);
    assertThat(filter).isEqualTo(filter2);
    final Filter<Short> filter3 = new Filter<>();
    filter3.setEquals(value);
    assertThat(filter3).isNotEqualTo(filter);
    assertThat(filter3).isNotEqualTo(filter2);

    assertThat(filter).isEqualTo(filter);
  }

  @Test
  public void testHashCode() {
    final RangeFilter<Short> filter2 = new RangeFilter<>();
    assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
    filter.setEquals(value);
    filter2.setEquals(value);
    assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
    filter.setIn(Lists.newArrayList(value, value));
    filter2.setIn(Lists.newArrayList(value, value));
    assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
    filter.setLessThan(value);
    filter2.setLessThan(value);
    assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
    filter.setGreaterThanOrEqual(value);
    filter2.setGreaterThanOrEqual(value);
    assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
    final Filter<Short> filter3 = new Filter<>();
    filter3.setEquals(value);
    assertThat(filter3.hashCode()).isNotEqualTo(filter.hashCode());
    assertThat(filter3.hashCode()).isNotEqualTo(filter2.hashCode());

    assertThat(filter.hashCode()).isEqualTo(filter.hashCode());
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
    assertThat(filter.toString()).isEqualTo("RangeFilter " + "[greaterThan=42, greaterThanOrEqual=42, lessThan=42, "
        + "lessThanOrEqual=42, equals=42, specified=true, in=[]]");
  }
}
