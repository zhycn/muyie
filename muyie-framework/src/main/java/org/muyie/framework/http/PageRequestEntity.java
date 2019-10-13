package org.muyie.framework.http;

import java.util.Optional;

/**
 * 分页请求实体
 */
public class PageRequestEntity extends RequestEntity {

  private static final long serialVersionUID = 1L;

  private PageQuery pageQuery;

  public PageQuery getPageQuery() {
    return Optional.ofNullable(pageQuery).orElse(new PageQuery());
  }

  public void setPageQuery(PageQuery pageQuery) {
    this.pageQuery = pageQuery;
  }

}
