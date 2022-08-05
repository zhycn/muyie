package org.muyie.framework.data.jpa.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class NotLikeSpecification<T> extends AbstractSpecification<T> {

  private static final long serialVersionUID = 1L;

  private final String property;
  private final List<String> patterns;

  public NotLikeSpecification(final String property, final List<String> patterns) {
    this(property, patterns, JoinType.INNER);
  }

  public NotLikeSpecification(final String property, final List<String> patterns, final JoinType joinType) {
    super(joinType);
    this.property = property;
    this.patterns = patterns;
  }

  @Override
  public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query,
                               final CriteriaBuilder criteriaBuilder) {
    final From<?, ?> from = getRoot(property, root);
    final String field = getProperty(property);

    final Predicate[] predicates = new Predicate[patterns.size()];
    for (int i = 0; i < patterns.size(); i++) {
      predicates[i] = criteriaBuilder.notLike(from.get(field), patterns.get(i));
    }

    return criteriaBuilder.or(predicates);
  }

}
