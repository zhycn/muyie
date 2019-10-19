package org.muyie.framework.http;

import java.io.Serializable;

/**
 * Builds the page queries
 */
public class PagerBuilder implements Serializable {

  private static final long serialVersionUID = 1L;

  private final Pager pager = new Pager();

  public PagerBuilder setEq(String eq) {
    this.pager.setEq(eq);
    return this;
  }

  public PagerBuilder setEt(String et) {
    this.pager.setEt(et);
    return this;
  }

  public PagerBuilder setF(String f) {
    this.pager.setF(f);
    return this;
  }

  public PagerBuilder setOrder(String order) {
    this.pager.setOrder(order);
    return this;
  }

  public PagerBuilder setPage(Integer page) {
    this.pager.setPage(page);
    return this;
  }

  public PagerBuilder setQ(String q) {
    this.pager.setQ(q);
    return this;
  }

  public PagerBuilder setSize(Integer size) {
    this.pager.setSize(size);
    return this;
  }

  public PagerBuilder setSn(String sn) {
    this.pager.setSn(sn);
    return this;
  }

  public PagerBuilder setSt(String st) {
    this.pager.setSt(st);
    return this;
  }

  public PagerBuilder setState(String state) {
    this.pager.setState(state);
    return this;
  }

  public Pager build() {
    return pager;
  }

}
