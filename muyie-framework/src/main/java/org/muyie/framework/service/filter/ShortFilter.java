package org.muyie.framework.service.filter;

/**
 * Filter class for {@link java.lang.Short} type attributes.
 *
 * @see RangeFilter
 */
public class ShortFilter extends RangeFilter<Short> {

  private static final long serialVersionUID = 1L;

  /**
   * <p>
   * Constructor for ShortFilter.
   * </p>
   */
  public ShortFilter() {}

  /**
   * <p>
   * Constructor for ShortFilter.
   * </p>
   *
   * @param filter a {@link org.muyie.framework.service.filter.ShortFilter} object.
   */
  public ShortFilter(final ShortFilter filter) {
    super(filter);
  }

  /**
   * <p>
   * copy.
   * </p>
   *
   * @return a {@link org.muyie.framework.service.filter.ShortFilter} object.
   */
  public ShortFilter copy() {
    return new ShortFilter(this);
  }

}
