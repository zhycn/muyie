package org.muyie.framework.http;

import java.io.Serializable;

/**
 * Builds the page queries
 */
public class PageQueryBuilder implements Serializable {

  private static final long serialVersionUID = 1L;

  private final PageQuery pageQuery = new PageQuery();

  public PageQueryBuilder setEq(String eq) {
    this.pageQuery.setEq(eq);
    return this;
  }

  public PageQueryBuilder setEt(String et) {
    this.pageQuery.setEt(et);
    return this;
  }

  public PageQueryBuilder setF(String f) {
    this.pageQuery.setF(f);
    return this;
  }

  public PageQueryBuilder setOrder(String order) {
    this.pageQuery.setOrder(order);
    return this;
  }

  public PageQueryBuilder setPage(Integer page) {
    this.pageQuery.setPage(page);
    return this;
  }

  public PageQueryBuilder setQ(String q) {
    this.pageQuery.setQ(q);
    return this;
  }

  public PageQueryBuilder setSize(Integer size) {
    this.pageQuery.setSize(size);
    return this;
  }

  public PageQueryBuilder setSn(String sn) {
    this.pageQuery.setSn(sn);
    return this;
  }

  public PageQueryBuilder setSt(String st) {
    this.pageQuery.setSt(st);
    return this;
  }

  public PageQueryBuilder setState(String state) {
    this.pageQuery.setState(state);
    return this;
  }

  public PageQuery build() {
    return pageQuery;
  }

}
