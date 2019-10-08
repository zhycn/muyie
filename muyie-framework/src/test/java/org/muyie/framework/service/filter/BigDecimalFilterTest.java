package org.muyie.framework.service.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.muyie.framework.service.filter.BigDecimalFilter;
import org.muyie.framework.service.filter.Filter;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BigDecimalFilterTest {

  private BigDecimalFilter filter;

  private BigDecimal value = new BigDecimal(42L);

  @BeforeEach
  public void setup() {
    filter = new BigDecimalFilter();
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
    assertThat(filter.toString()).isEqualTo("BigDecimalFilter []");
  }

  @Test
  public void testSetEquals() {
    Filter<BigDecimal> chain = filter.setEquals(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getEquals()).isEqualTo(value);
  }

  @Test
  public void testSetLessThan() {
    Filter<BigDecimal> chain = filter.setLessThan(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getLessThan()).isEqualTo(value);
  }

  @Test
  public void testSetLessThanOrEqual() {
    Filter<BigDecimal> chain = filter.setLessThanOrEqual(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getLessThanOrEqual()).isEqualTo(value);
  }

  @Test
  public void testSetGreaterThan() {
    Filter<BigDecimal> chain = filter.setGreaterThan(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getGreaterThan()).isEqualTo(value);
  }

  @Test
  public void testSetGreaterThanOrEqual() {
    Filter<BigDecimal> chain = filter.setGreaterThanOrEqual(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getGreaterThanOrEqual()).isEqualTo(value);
  }

  @Test
  public void testSetSpecified() {
    Filter<BigDecimal> chain = filter.setSpecified(true);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getSpecified()).isEqualTo(true);
  }

  @Test
  public void testSetIn() {
    List<BigDecimal> list = new LinkedList<>();
    Filter<BigDecimal> chain = filter.setIn(list);
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
    assertThat(filter.toString()).isEqualTo("BigDecimalFilter " + "[greaterThan=" + str
        + ", greaterThanOrEqual=" + str + ", lessThan=" + str + ", " + "lessThanOrEqual=" + str
        + ", equals=" + str + ", specified=true, in=[]]");
  }
}
