package org.muyie.framework.service.filter;

import com.google.common.collect.Lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.muyie.framework.service.filter.Filter;
import org.muyie.framework.service.filter.StringFilter;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StringFilterTest {

  private StringFilter filter;

  private String value = "foo";

  @BeforeEach
  public void setup() {
    filter = new StringFilter();
  }

  @Test
  public void testConstructor() {
    assertThat(filter.getEquals()).isNull();
    assertThat(filter.getContains()).isNull();
    assertThat(filter.getSpecified()).isNull();
    assertThat(filter.getIn()).isNull();
    assertThat(filter.toString()).isEqualTo("StringFilter []");
  }

  @Test
  public void testSetEquals() {
    Filter<String> chain = filter.setEquals(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getEquals()).isEqualTo(value);
  }

  @Test
  public void testSetContains() {
    Filter<String> chain = filter.setContains(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getContains()).isEqualTo(value);
  }

  @Test
  public void testSetSpecified() {
    Filter<String> chain = filter.setSpecified(true);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getSpecified()).isEqualTo(true);
  }

  @Test
  public void testSetIn() {
    List<String> list = new LinkedList<>();
    Filter<String> chain = filter.setIn(list);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getIn()).isEqualTo(list);
  }

  @Test
  public void testEquals() {
    final StringFilter filter2 = new StringFilter();
    assertThat(filter).isEqualTo(filter2);
    filter.setEquals(value);
    filter2.setEquals(value);
    assertThat(filter).isEqualTo(filter2);
    filter.setIn(Lists.newArrayList(value, value));
    filter2.setIn(Lists.newArrayList(value, value));
    assertThat(filter).isEqualTo(filter2);
    filter.setContains(value);
    filter2.setContains(value);
    assertThat(filter).isEqualTo(filter2);
    final StringFilter filter3 = new StringFilter();
    filter3.setEquals(value);
    assertThat(filter3).isNotEqualTo(filter);
    assertThat(filter3).isNotEqualTo(filter2);

    assertThat(filter).isEqualTo(filter);
  }

  @Test
  public void testHashCode() {
    final StringFilter filter2 = new StringFilter();
    assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
    filter.setEquals(value);
    filter2.setEquals(value);
    assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
    filter.setIn(Lists.newArrayList(value, value));
    filter2.setIn(Lists.newArrayList(value, value));
    assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
    filter.setContains(value);
    filter2.setContains(value);
    assertThat(filter.hashCode()).isEqualTo(filter2.hashCode());
    final StringFilter filter3 = new StringFilter();
    filter3.setEquals(value);
    assertThat(filter3.hashCode()).isNotEqualTo(filter.hashCode());
    assertThat(filter3.hashCode()).isNotEqualTo(filter2.hashCode());

    assertThat(filter.hashCode()).isEqualTo(filter.hashCode());
  }

  @Test
  public void testToString() {
    filter.setEquals(value);
    filter.setContains(value);
    filter.setSpecified(true);
    filter.setIn(new LinkedList<>());
    assertThat(filter.toString())
        .isEqualTo("StringFilter [contains=foo, equals=foo, specified=true]");
  }
}
