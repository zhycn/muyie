package org.muyie.framework.http;

import java.util.Optional;

/**
 * 分页和签名请求实体
 */
public class PageAndSignRequestEntity extends SignRequestEntity {

  private static final long serialVersionUID = 1L;

  private PageQuery pageQuery;

  public PageQuery getPageQuery() {
    return Optional.ofNullable(pageQuery).orElse(new PageQuery());
  }

  public void setPageQuery(PageQuery pageQuery) {
    this.pageQuery = pageQuery;
  }

}
