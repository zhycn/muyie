package org.muyie.framework.http;

import java.util.Optional;

public class PageAndSignRequest extends SignRequest {

  private static final long serialVersionUID = 1L;

  private Pager pager;

  public Pager getPager() {
    return Optional.ofNullable(pager).orElse(new Pager());
  }

  public void setPager(Pager pager) {
    this.pager = pager;
  }

}
