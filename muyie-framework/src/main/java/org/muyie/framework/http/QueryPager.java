package org.muyie.framework.http;

import javax.validation.constraints.Min;

public class QueryPager<T> extends Request {

  private static final long serialVersionUID = 1L;

  private T body;

  @Min(1)
  private Integer page = 1;

  @Min(1)
  private Integer size = 10;

  public T getBody() {
    return body;
  }

  public void setBody(T body) {
    this.body = body;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

}
