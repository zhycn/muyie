package org.muyie.framework.domain.service.filter;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.Instant;

/**
 * Filter class for {@link java.time.Instant} type attributes.
 *
 * @see RangeFilter
 */
public class InstantFilter extends RangeFilter<Instant> {

  private static final long serialVersionUID = 1L;

  /**
   * <p>
   * Constructor for InstantFilter.
   * </p>
   */
  public InstantFilter() {}

  /**
   * <p>
   * Constructor for InstantFilter.
   * </p>
   *
   * @param filter a {@link org.muyie.framework.domain.service.filter.InstantFilter} object.
   */
  public InstantFilter(final InstantFilter filter) {
    super(filter);
  }

  /**
   * <p>
   * copy.
   * </p>
   *
   * @return a {@link org.muyie.framework.domain.service.filter.InstantFilter} object.
   */
  public InstantFilter copy() {
    return new InstantFilter(this);
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public InstantFilter setEquals(Instant equals) {
    super.setEquals(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public InstantFilter setGreaterThan(Instant equals) {
    super.setGreaterThan(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public InstantFilter setGreaterThanOrEqual(Instant equals) {
    super.setGreaterThanOrEqual(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  @Deprecated
  public InstantFilter setGreaterOrEqualThan(Instant equals) {
    super.setGreaterOrEqualThan(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public InstantFilter setLessThan(Instant equals) {
    super.setLessThan(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  public InstantFilter setLessThanOrEqual(Instant equals) {
    super.setLessThanOrEqual(equals);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  @DateTimeFormat(iso = ISO.DATE_TIME)
  @Deprecated
  public InstantFilter setLessOrEqualThan(Instant equals) {
    super.setLessOrEqualThan(equals);
    return this;
  }

}
