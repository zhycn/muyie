package org.muyie.framework.domain.service.filter;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.LocalDate;
import java.util.List;

/**
 * Filter class for {@link java.time.LocalDate} type attributes.
 *
 * @see RangeFilter
 */
public class LocalDateFilter extends RangeFilter<LocalDate> {

  private static final long serialVersionUID = 1L;

  /**
   * <p>
   * Constructor for LocalDateFilter.
   * </p>
   */
  public LocalDateFilter() {}

  /**
   * <p>
   * Constructor for LocalDateFilter.
   * </p>
   *
   * @param filter a {@link org.muyie.framework.domain.service.filter.LocalDateFilter} object.
   */
  public LocalDateFilter(final LocalDateFilter filter) {
    super(filter);
  }

  /**
   * <p>
   * copy.
   * </p>
   *
   * @return a {@link org.muyie.framework.domain.service.filter.LocalDateFilter} object.
   */
  public LocalDateFilter copy() {
    return new LocalDateFilter(this);
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setEquals(LocalDate equals) {
    super.setEquals(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setGreaterThan(LocalDate equals) {
    super.setGreaterThan(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setGreaterThanOrEqual(LocalDate equals) {
    super.setGreaterThanOrEqual(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  @Deprecated
  public LocalDateFilter setGreaterOrEqualThan(LocalDate equals) {
    super.setGreaterOrEqualThan(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setLessThan(LocalDate equals) {
    super.setLessThan(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setLessThanOrEqual(LocalDate equals) {
    super.setLessThanOrEqual(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  @Deprecated
  public LocalDateFilter setLessOrEqualThan(LocalDate equals) {
    super.setLessOrEqualThan(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE)
  public LocalDateFilter setIn(List<LocalDate> in) {
    super.setIn(in);
    return this;
  }

}
