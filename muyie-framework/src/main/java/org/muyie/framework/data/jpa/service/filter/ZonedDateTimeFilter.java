package org.muyie.framework.data.jpa.service.filter;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Filter class for {@link ZonedDateTime} type attributes.
 *
 * @see RangeFilter
 */
public class ZonedDateTimeFilter extends RangeFilter<ZonedDateTime> {

  private static final long serialVersionUID = 1L;

  /**
   * <p>
   * Constructor for ZonedDateTimeFilter.
   * </p>
   */
  public ZonedDateTimeFilter() {
  }

  /**
   * <p>
   * Constructor for ZonedDateTimeFilter.
   * </p>
   *
   * @param filter a {@link org.muyie.framework.data.jpa.service.filter.ZonedDateTimeFilter}
   *               object.
   */
  public ZonedDateTimeFilter(final ZonedDateTimeFilter filter) {
    super(filter);
  }

  /**
   * <p>
   * copy.
   * </p>
   *
   * @return a {@link org.muyie.framework.data.jpa.service.filter.ZonedDateTimeFilter} object.
   */
  public ZonedDateTimeFilter copy() {
    return new ZonedDateTimeFilter(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public ZonedDateTimeFilter setEquals(ZonedDateTime equals) {
    super.setEquals(equals);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public ZonedDateTimeFilter setGreaterThan(ZonedDateTime equals) {
    super.setGreaterThan(equals);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public ZonedDateTimeFilter setGreaterThanOrEqual(ZonedDateTime equals) {
    super.setGreaterThanOrEqual(equals);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  @Deprecated
  public ZonedDateTimeFilter setGreaterOrEqualThan(ZonedDateTime equals) {
    super.setGreaterOrEqualThan(equals);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public ZonedDateTimeFilter setLessThan(ZonedDateTime equals) {
    super.setLessThan(equals);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public ZonedDateTimeFilter setLessThanOrEqual(ZonedDateTime equals) {
    super.setLessThanOrEqual(equals);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  @Deprecated
  public ZonedDateTimeFilter setLessOrEqualThan(ZonedDateTime equals) {
    super.setLessOrEqualThan(equals);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public ZonedDateTimeFilter setIn(List<ZonedDateTime> in) {
    super.setIn(in);
    return this;
  }
}
