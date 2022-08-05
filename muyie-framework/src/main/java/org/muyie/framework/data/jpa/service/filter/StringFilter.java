package org.muyie.framework.data.jpa.service.filter;

import java.util.Objects;

/**
 * Class for filtering attributes with {@link String} type. It can be added to a criteria class as a
 * member, to support the following query parameters: <code> fieldName.equals='something'
 * fieldName.specified=true fieldName.specified=false fieldName.in='something','other'
 * fieldName.contains='thing'
 * </code>
 */
public class StringFilter extends Filter<String> {

  private static final long serialVersionUID = 1L;

  private String contains;

  /**
   * <p>
   * Constructor for StringFilter.
   * </p>
   */
  public StringFilter() {
  }

  /**
   * <p>
   * Constructor for StringFilter.
   * </p>
   *
   * @param filter a {@link org.muyie.framework.data.jpa.service.filter.StringFilter} object.
   */
  public StringFilter(final StringFilter filter) {
    super(filter);
    this.contains = filter.contains;
  }

  /**
   * <p>
   * copy.
   * </p>
   *
   * @return a {@link org.muyie.framework.data.jpa.service.filter.StringFilter} object.
   */
  public StringFilter copy() {
    return new StringFilter(this);
  }

  /**
   * <p>
   * Getter for the field <code>contains</code>.
   * </p>
   *
   * @return a {@link String} object.
   */
  public String getContains() {
    return contains;
  }

  /**
   * <p>
   * Setter for the field <code>contains</code>.
   * </p>
   *
   * @param contains a {@link String} object.
   * @return a {@link org.muyie.framework.data.jpa.service.filter.StringFilter} object.
   */
  public StringFilter setContains(String contains) {
    this.contains = contains;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    final StringFilter that = (StringFilter) o;
    return Objects.equals(contains, that.contains);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), contains);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return getFilterName() + " ["
      + (getContains() != null ? "contains=" + getContains() + ", " : "")
      + (getEquals() != null ? "equals=" + getEquals() + ", " : "")
      + (getSpecified() != null ? "specified=" + getSpecified() : "") + "]";
  }

}
