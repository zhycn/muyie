package org.muyie.framework.data.jpa.service.filter;

import java.util.UUID;

/**
 * Filter class for {@link UUID} type attributes.
 *
 * @see Filter
 */
public class UUIDFilter extends Filter<UUID> {

  private static final long serialVersionUID = 1L;

  /**
   * <p>
   * Constructor for UUIDFilter.
   * </p>
   */
  public UUIDFilter() {
  }

  /**
   * <p>
   * Constructor for UUIDFilter.
   * </p>
   *
   * @param filter a {@link org.muyie.framework.data.jpa.service.filter.UUIDFilter} object.
   */
  public UUIDFilter(final UUIDFilter filter) {
    super(filter);
  }

  /**
   * <p>
   * copy.
   * </p>
   *
   * @return a {@link org.muyie.framework.data.jpa.service.filter.UUIDFilter} object.
   */
  public UUIDFilter copy() {
    return new UUIDFilter(this);
  }

}
