package com.muyie.orm.jpa.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class InSpecification<T> extends AbstractSpecification<T> {

  private static final long serialVersionUID = 1L;

  private final String property;
  private final List<Object> values;

  public InSpecification(final String property, final List<Object> values) {
    this(property, values, JoinType.INNER);
  }

  public InSpecification(final String property, final List<Object> values, final JoinType joinType) {
    super(joinType);
    this.property = property;
    this.values = values;
  }

  @Override
  public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query,
                               final CriteriaBuilder criteriaBuilder) {
    final From<?, ?> from = getRoot(property, root);
    final String field = getProperty(property);
    return from.get(field).in(values);
  }

}
