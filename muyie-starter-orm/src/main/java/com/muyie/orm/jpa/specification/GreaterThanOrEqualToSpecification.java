package com.muyie.orm.jpa.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class GreaterThanOrEqualToSpecification<T> extends AbstractSpecification<T> {

  private static final long serialVersionUID = 1L;

  private final String property;
  private final Comparable<Object> compare;

  public GreaterThanOrEqualToSpecification(final String property, final Comparable<? extends Object> compare) {
    this(property, compare, JoinType.INNER);
  }

  @SuppressWarnings("unchecked")
  public GreaterThanOrEqualToSpecification(final String property, final Comparable<? extends Object> compare,
                                           final JoinType joinType) {
    super(joinType);
    this.property = property;
    this.compare = (Comparable<Object>) compare;
  }

  @Override
  public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query,
                               final CriteriaBuilder criteriaBuilder) {
    final From<?, ?> from = getRoot(property, root);
    final String field = getProperty(property);
    return criteriaBuilder.greaterThanOrEqualTo(from.get(field), compare);
  }

}
