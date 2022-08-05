package org.muyie.framework.data.jpa.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static javax.persistence.criteria.Predicate.BooleanOperator.OR;

public class PredicateBuilder<T> {

  private final Predicate.BooleanOperator operator;
  private final List<Specification<T>> specifications;

  public PredicateBuilder(final Predicate.BooleanOperator operator) {
    this.operator = operator;
    this.specifications = new ArrayList<>();
  }

  public PredicateBuilder<T> equal(final String property, final List<Object> values) {
    return this.predicate(new EqualSpecification<>(property, values));
  }

  public PredicateBuilder<T> equal(final String property, final Object... values) {
    return this.predicate(new EqualSpecification<>(property, Arrays.asList(values)));
  }

  public PredicateBuilder<T> equal(final String property, final JoinType joinType, final List<Object> values) {
    return this.predicate(new EqualSpecification<>(property, values, joinType));
  }

  public PredicateBuilder<T> equal(final String property, final JoinType joinType, final Object... values) {
    return this.predicate(new EqualSpecification<>(property, Arrays.asList(values), joinType));
  }

  public PredicateBuilder<T> equal(final boolean condition, final String property, final List<Object> values) {
    return this.predicate(condition, new EqualSpecification<>(property, values));
  }

  public PredicateBuilder<T> equal(final boolean condition, final String property, final Object... values) {
    return this.predicate(condition, new EqualSpecification<>(property, Arrays.asList(values)));
  }

  public PredicateBuilder<T> equal(final boolean condition, final String property, final JoinType joinType,
                                   final List<Object> values) {
    return this.predicate(condition, new EqualSpecification<>(property, values, joinType));
  }

  public PredicateBuilder<T> equal(final boolean condition, final String property, final JoinType joinType,
                                   final Object... values) {
    return this.predicate(condition, new EqualSpecification<>(property, Arrays.asList(values), joinType));
  }

  public PredicateBuilder<T> notEqual(final String property, final List<Object> values) {
    return this.predicate(new NotEqualSpecification<>(property, values));
  }

  public PredicateBuilder<T> notEqual(final String property, final Object... values) {
    return this.predicate(new NotEqualSpecification<>(property, Arrays.asList(values)));
  }

  public PredicateBuilder<T> notEqual(final String property, final JoinType joinType, final List<Object> values) {
    return this.predicate(new NotEqualSpecification<>(property, values, joinType));
  }

  public PredicateBuilder<T> notEqual(final String property, final JoinType joinType, final Object... values) {
    return this.predicate(new NotEqualSpecification<>(property, Arrays.asList(values), joinType));
  }

  public PredicateBuilder<T> notEqual(final boolean condition, final String property, final List<Object> values) {
    return this.predicate(condition, new NotEqualSpecification<>(property, values));
  }

  public PredicateBuilder<T> notEqual(final boolean condition, final String property, final Object... values) {
    return this.predicate(condition, new NotEqualSpecification<>(property, Arrays.asList(values)));
  }

  public PredicateBuilder<T> notEqual(final boolean condition, final String property, final JoinType joinType,
                                      final List<Object> values) {
    return this.predicate(condition, new NotEqualSpecification<>(property, values, joinType));
  }

  public PredicateBuilder<T> notEqual(final boolean condition, final String property, final JoinType joinType,
                                      final Object... values) {
    return this.predicate(condition, new NotEqualSpecification<>(property, Arrays.asList(values), joinType));
  }

  public PredicateBuilder<T> greaterThan(final String property, final Comparable<?> compare) {
    return this.predicate(new GreaterThanSpecification<>(property, compare));
  }

  public PredicateBuilder<T> greaterThan(final String property, final Comparable<?> compare, final JoinType joinType) {
    return this.predicate(new GreaterThanSpecification<>(property, compare, joinType));
  }

  public PredicateBuilder<T> greaterThan(final boolean condition, final String property, final Comparable<?> compare) {
    return this.predicate(condition, new GreaterThanSpecification<>(property, compare));
  }

  public PredicateBuilder<T> greaterThan(final boolean condition, final String property, final Comparable<?> compare,
                                         final JoinType joinType) {
    return this.predicate(condition, new GreaterThanSpecification<>(property, compare, joinType));
  }

  public PredicateBuilder<T> greaterThanOrEqualTo(final String property, final Comparable<?> compare) {
    return this.predicate(new GreaterThanOrEqualToSpecification<>(property, compare));
  }

  public PredicateBuilder<T> greaterThanOrEqualTo(final String property, final Comparable<?> compare,
                                                  final JoinType joinType) {
    return this.predicate(new GreaterThanOrEqualToSpecification<>(property, compare, joinType));
  }

  public PredicateBuilder<T> greaterThanOrEqualTo(final boolean condition, final String property,
                                                  final Comparable<?> compare) {
    return this.predicate(condition, new GreaterThanOrEqualToSpecification<>(property, compare));
  }

  public PredicateBuilder<T> greaterThanOrEqualTo(final boolean condition, final String property,
                                                  final Comparable<?> compare, final JoinType joinType) {
    return this.predicate(condition, new GreaterThanOrEqualToSpecification<>(property, compare, joinType));
  }

  public PredicateBuilder<T> lessThan(final String property, final Comparable<?> compare) {
    return this.predicate(new LessThanSpecification<>(property, compare));
  }

  public PredicateBuilder<T> lessThan(final String property, final Comparable<?> compare, final JoinType joinType) {
    return this.predicate(new LessThanSpecification<>(property, compare, joinType));
  }

  public PredicateBuilder<T> lessThan(final boolean condition, final String property, final Comparable<?> compare) {
    return this.predicate(condition, new LessThanSpecification<>(property, compare));
  }

  public PredicateBuilder<T> lessThan(final boolean condition, final String property, final Comparable<?> compare,
                                      final JoinType joinType) {
    return this.predicate(condition, new LessThanSpecification<>(property, compare, joinType));
  }

  public PredicateBuilder<T> lessThanOrEqualTo(final String property, final Comparable<?> compare) {
    return this.predicate(new LessThanOrEqualToSpecification<>(property, compare));
  }

  public PredicateBuilder<T> lessThanOrEqualTo(final String property, final Comparable<?> compare,
                                               final JoinType joinType) {
    return this.predicate(new LessThanOrEqualToSpecification<>(property, compare, joinType));
  }

  public PredicateBuilder<T> lessThanOrEqualTo(final boolean condition, final String property,
                                               final Comparable<?> compare) {
    return this.predicate(condition, new LessThanOrEqualToSpecification<>(property, compare));
  }

  public PredicateBuilder<T> lessThanOrEqualTo(final boolean condition, final String property,
                                               final Comparable<?> compare, final JoinType joinType) {
    return this.predicate(condition, new LessThanOrEqualToSpecification<>(property, compare, joinType));
  }

  public PredicateBuilder<T> between(final String property, final Object lower, final Object upper) {
    return this.predicate(new BetweenSpecification<>(property, lower, upper));
  }

  public PredicateBuilder<T> between(final String property, final Object lower, final Object upper,
                                     final JoinType joinType) {
    return this.predicate(new BetweenSpecification<>(property, lower, upper, joinType));
  }

  public PredicateBuilder<T> between(final boolean condition, final String property, final Object lower,
                                     final Object upper) {
    return this.predicate(condition, new BetweenSpecification<>(property, lower, upper));
  }

  public PredicateBuilder<T> between(final boolean condition, final String property, final Object lower,
                                     final Object upper, final JoinType joinType) {
    return this.predicate(condition, new BetweenSpecification<>(property, lower, upper, joinType));
  }

  public PredicateBuilder<T> like(final String property, final List<String> patterns) {
    return this.predicate(new LikeSpecification<T>(property, patterns));
  }

  public PredicateBuilder<T> like(final String property, final String... patterns) {
    return this.predicate(new LikeSpecification<T>(property, Arrays.asList(patterns)));
  }

  public PredicateBuilder<T> like(final String property, final JoinType joinType, final List<String> patterns) {
    return this.predicate(new LikeSpecification<T>(property, patterns, joinType));
  }

  public PredicateBuilder<T> like(final String property, final JoinType joinType, final String... patterns) {
    return this.predicate(new LikeSpecification<T>(property, Arrays.asList(patterns), joinType));
  }

  public PredicateBuilder<T> like(final boolean condition, final String property, final List<String> patterns) {
    return this.predicate(condition, new LikeSpecification<T>(property, patterns));
  }

  public PredicateBuilder<T> like(final boolean condition, final String property, final String... patterns) {
    return this.predicate(condition, new LikeSpecification<T>(property, Arrays.asList(patterns)));
  }

  public PredicateBuilder<T> like(final boolean condition, final String property, final JoinType joinType,
                                  final List<String> patterns) {
    return this.predicate(condition, new LikeSpecification<T>(property, patterns, joinType));
  }

  public PredicateBuilder<T> like(final boolean condition, final String property, final JoinType joinType,
                                  final String... patterns) {
    return this.predicate(condition, new LikeSpecification<T>(property, Arrays.asList(patterns), joinType));
  }

  public PredicateBuilder<T> notLike(final String property, final List<String> patterns) {
    return this.predicate(new NotLikeSpecification<T>(property, patterns));
  }

  public PredicateBuilder<T> notLike(final String property, final String... patterns) {
    return this.predicate(new NotLikeSpecification<T>(property, Arrays.asList(patterns)));
  }

  public PredicateBuilder<T> notLike(final String property, final JoinType joinType, final List<String> patterns) {
    return this.predicate(new NotLikeSpecification<T>(property, patterns, joinType));
  }

  public PredicateBuilder<T> notLike(final String property, final JoinType joinType, final String... patterns) {
    return this.predicate(new NotLikeSpecification<T>(property, Arrays.asList(patterns), joinType));
  }

  public PredicateBuilder<T> notLike(final boolean condition, final String property, final List<String> patterns) {
    return this.predicate(condition, new NotLikeSpecification<T>(property, patterns));
  }

  public PredicateBuilder<T> notLike(final boolean condition, final String property, final String... patterns) {
    return this.predicate(condition, new NotLikeSpecification<T>(property, Arrays.asList(patterns)));
  }

  public PredicateBuilder<T> notLike(final boolean condition, final String property, final JoinType joinType,
                                     final List<String> patterns) {
    return this.predicate(condition, new NotLikeSpecification<T>(property, patterns, joinType));
  }

  public PredicateBuilder<T> notLike(final boolean condition, final String property, final JoinType joinType,
                                     final String... patterns) {
    return this.predicate(condition, new NotLikeSpecification<T>(property, Arrays.asList(patterns), joinType));
  }

  public PredicateBuilder<T> in(final String property, final List<Object> values) {
    return this.predicate(new InSpecification<T>(property, values));
  }

  public PredicateBuilder<T> in(final String property, final Object... values) {
    return this.predicate(new InSpecification<T>(property, Arrays.asList(values)));
  }

  public PredicateBuilder<T> in(final String property, final JoinType joinType, final List<Object> values) {
    return this.predicate(new InSpecification<T>(property, values, joinType));
  }

  public PredicateBuilder<T> in(final String property, final JoinType joinType, final Object... values) {
    return this.predicate(new InSpecification<T>(property, Arrays.asList(values), joinType));
  }

  public PredicateBuilder<T> in(final boolean condition, final String property, final List<Object> values) {
    return this.predicate(condition, new InSpecification<T>(property, values));
  }

  public PredicateBuilder<T> in(final boolean condition, final String property, final Object... values) {
    return this.predicate(condition, new InSpecification<T>(property, Arrays.asList(values)));
  }

  public PredicateBuilder<T> in(final boolean condition, final String property, final JoinType joinType,
                                final List<Object> values) {
    return this.predicate(condition, new InSpecification<T>(property, values, joinType));
  }

  public PredicateBuilder<T> in(final boolean condition, final String property, final JoinType joinType,
                                final Object... values) {
    return this.predicate(condition, new InSpecification<T>(property, Arrays.asList(values), joinType));
  }

  public PredicateBuilder<T> notIn(final String property, final List<Object> values) {
    return this.predicate(new NotInSpecification<T>(property, values));
  }

  public PredicateBuilder<T> notIn(final String property, final Object... values) {
    return this.predicate(new NotInSpecification<T>(property, Arrays.asList(values)));
  }

  public PredicateBuilder<T> notIn(final String property, final JoinType joinType, final List<Object> values) {
    return this.predicate(new NotInSpecification<T>(property, values, joinType));
  }

  public PredicateBuilder<T> notIn(final String property, final JoinType joinType, final Object... values) {
    return this.predicate(new NotInSpecification<T>(property, Arrays.asList(values), joinType));
  }

  public PredicateBuilder<T> notIn(final boolean condition, final String property, final List<Object> values) {
    return this.predicate(condition, new NotInSpecification<T>(property, values));
  }

  public PredicateBuilder<T> notIn(final boolean condition, final String property, final Object... values) {
    return this.predicate(condition, new NotInSpecification<T>(property, Arrays.asList(values)));
  }

  public PredicateBuilder<T> notIn(final boolean condition, final String property, final JoinType joinType,
                                   final List<Object> values) {
    return this.predicate(condition, new NotInSpecification<T>(property, values, joinType));
  }

  public PredicateBuilder<T> notIn(final boolean condition, final String property, final JoinType joinType,
                                   final Object... values) {
    return this.predicate(condition, new NotInSpecification<T>(property, Arrays.asList(values), joinType));
  }

  public PredicateBuilder<T> predicate(final Specification<T> specification) {
    return predicate(true, specification);
  }

  public PredicateBuilder<T> predicate(final boolean condition, final Specification<T> specification) {
    if (condition) {
      this.specifications.add(specification);
    }
    return this;
  }

  public Specification<T> build() {
    return (final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) -> {
      final Predicate[] predicates = new Predicate[specifications.size()];
      for (int i = 0; i < specifications.size(); i++) {
        predicates[i] = specifications.get(i).toPredicate(root, query, cb);
      }
      if (Objects.equals(predicates.length, 0)) {
        return null;
      }
      return OR.equals(operator) ? cb.or(predicates) : cb.and(predicates);
    };
  }

}
