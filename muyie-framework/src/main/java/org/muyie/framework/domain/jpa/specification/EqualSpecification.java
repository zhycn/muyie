package org.muyie.framework.domain.jpa.specification;

import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.CollectionUtils;

public class EqualSpecification<T> extends AbstractSpecification<T> {

  private static final long serialVersionUID = 1L;

  private final String property;
  private final List<Object> values;

  public EqualSpecification(final String property, final List<Object> values) {
    this(property, values, JoinType.INNER);
  }

  public EqualSpecification(final String property, final List<Object> values, final JoinType joinType) {
    super(joinType);
    this.property = property;
    this.values = values;
  }

  @Override
  public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query,
      final CriteriaBuilder criteriaBuilder) {
    final From<?, ?> from = getRoot(property, root);
    final String field = getProperty(property);

    if (CollectionUtils.isEmpty(values)) {
      return criteriaBuilder.isNull(from.get(field));
    }

    if (values.size() == 1) {
      return getPredicate(from, criteriaBuilder, field, values.get(0));
    }

    final Predicate[] predicates = new Predicate[values.size()];
    for (int i = 0; i < values.size(); i++) {
      predicates[i] = getPredicate(from, criteriaBuilder, field, values.get(i));
    }
    return criteriaBuilder.or(predicates);
  }

  private Predicate getPredicate(final From<?, ?> from, final CriteriaBuilder criteriaBuilder, final String field,
      final Object value) {
    return Objects.isNull(value) ? criteriaBuilder.isNull(from.get(field))
        : criteriaBuilder.equal(from.get(field), value);
  }

}
