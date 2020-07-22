package org.muyie.framework.domain.service.filter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UUIDFilterTest {

  private UUIDFilter filter;

  private final UUID value = UUID.fromString("dbc36987-d354-4ddf-9b53-38ca19b5a409");

  @BeforeEach
  public void setup() {
    filter = new UUIDFilter();
  }

  @Test
  public void testConstructor() {
    assertThat(filter.getEquals()).isNull();
    assertThat(filter.getSpecified()).isNull();
    assertThat(filter.getIn()).isNull();
    assertThat(filter.toString()).isEqualTo("UUIDFilter []");
  }

  @Test
  public void testSetEquals() {
    final Filter<UUID> chain = filter.setEquals(value);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getEquals()).isEqualTo(value);
  }

  @Test
  public void testSetSpecified() {
    final Filter<UUID> chain = filter.setSpecified(true);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getSpecified()).isEqualTo(true);
  }

  @Test
  public void testSetIn() {
    final List<UUID> list = new LinkedList<>();
    final Filter<UUID> chain = filter.setIn(list);
    assertThat(chain).isEqualTo(filter);
    assertThat(filter.getIn()).isEqualTo(list);
  }

  @Test
  public void testToString() {
    filter.setEquals(value);
    filter.setSpecified(true);
    filter.setIn(new LinkedList<>());
    assertThat(filter.toString())
        .isEqualTo("UUIDFilter [equals=dbc36987-d354-4ddf-9b53-38ca19b5a409, in=[], specified=true]");
  }
}
