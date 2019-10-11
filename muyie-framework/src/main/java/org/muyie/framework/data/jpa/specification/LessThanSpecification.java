package org.muyie.framework.data.jpa.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class LessThanSpecification<T> extends AbstractSpecification<T> {

  private static final long serialVersionUID = 1L;

  private final String property;
  private final Comparable<Object> compare;

  public LessThanSpecification(String property, Comparable<? extends Object> compare) {
    this(property, compare, JoinType.INNER);
  }

  @SuppressWarnings("unchecked")
  public LessThanSpecification(String property, Comparable<? extends Object> compare,
      JoinType joinType) {
    super(joinType);
    this.property = property;
    this.compare = (Comparable<Object>) compare;
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder) {
    From<?, ?> from = getRoot(property, root);
    String field = getProperty(property);
    return criteriaBuilder.lessThan(from.get(field), compare);
  }
  
}
