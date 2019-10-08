package org.muyie.framework.service.filter;

import com.google.common.collect.Lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.muyie.framework.service.filter.Filter;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FilterTest {

  private Filter<Object> filter;

  private Object value = new Object() {
    @Override
    public String toString() {
      return "{}";
    }
  };

  @BeforeEach
  public void setup() {
    filter = new Filter<Object>();
  }

  @Test
  public void testConstructor() {
    assertThat(filter.getEquals()).isNull();
    assertThat(filter.getSpecified()).isNull();
    assertThat(filter.getIn()).isNull();
    assertThat(filter.toString()).isEqualTo("Filter []");
  }

  @Test
  public void testCopy() {
    final Filter<Object> copy = filter.copy();
    assertThat(copy).isNotSameAs(filter);
    assertThat(copy.getEquals()).isNull();
    assertThat(copy.getSpecified()).isNull();
    assertThat(copy.getIn()).isNull();
    assertThat(copy.toString()).isEqualTo("Filter []");
  }

  @Test
  public void testSetEquals() {
    Filter<Object> chain = filter.setEquals(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getEquals()).isEqualTo(value);
  }

  @Test
  public void testSetSpecified() {
    Filter<Object> chain = filter.setSpecified(true);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getSpecified()).isEqualTo(true);
  }

  @Test
  public void testSetIn() {
    List<Object> list = new LinkedList<>();
    Filter<Object> chain = filter.setIn(list);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getIn()).isEqualTo(list);
  }

  @Test
  public void testEquals() {
    final Filter<Object> filter2 = new Filter<>();
    assertThat(filter).isEqualTo(filter2);
    filter.setEquals(value);
    filter2.setEquals(value);
    assertThat(filter).isEqualTo(filter2);
    filter.setIn(Lists.newArrayList(value, value));
    filter2.setIn(Lists.newArrayList(value, value));
    assertThat(filter).isEqualTo(filter2);
    final Filter<Object> filter3 = new Filter<>();
    filter3.setEquals(value);
    assertThat(filter3).isNotEqualTo(filter);
    assertThat(filter3).isNotEqualTo(filter2);

    assertThat(filter).isEqualTo(filter);
  }

  @Test
  public void testHashCode() {
    final Filter<Object> filter2 = new Filter<>();
    assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
    filter.setEquals(value);
    filter2.setEquals(value);
    assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
    filter.setIn(Lists.newArrayList(value, value));
    filter2.setIn(Lists.newArrayList(value, value));
    assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
    final Filter<Object> filter3 = new Filter<>();
    filter3.setEquals(value);
    assertThat(filter3.hashCode()).isNotEqualTo(filter.hashCode());
    assertThat(filter3.hashCode()).isNotEqualTo(filter2.hashCode());

    assertThat(filter.hashCode()).isEqualTo(filter.hashCode());
  }

  @Test
  public void testToString() {
    filter.setEquals(value);
    filter.setSpecified(true);
    filter.setIn(new LinkedList<>());
    assertThat(filter.toString()).isEqualTo("Filter [equals={}, in=[], specified=true]");
  }
}
