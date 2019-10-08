package org.muyie.framework.service.filter;

/**
 * Filter class for {@link java.lang.Float} type attributes.
 *
 * @see RangeFilter
 */
public class FloatFilter extends RangeFilter<Float> {

  private static final long serialVersionUID = 1L;

  /**
   * <p>
   * Constructor for FloatFilter.
   * </p>
   */
  public FloatFilter() {}

  /**
   * <p>
   * Constructor for FloatFilter.
   * </p>
   *
   * @param filter a {@link org.muyie.framework.service.filter.FloatFilter} object.
   */
  public FloatFilter(final FloatFilter filter) {
    super(filter);
  }

  /**
   * <p>
   * copy.
   * </p>
   *
   * @return a {@link org.muyie.framework.service.filter.FloatFilter} object.
   */
  public FloatFilter copy() {
    return new FloatFilter(this);
  }

}
