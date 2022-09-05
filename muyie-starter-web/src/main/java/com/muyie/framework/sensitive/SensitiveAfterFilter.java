package com.muyie.framework.sensitive;

import com.alibaba.fastjson2.filter.AfterFilter;

/**
 * @author larry.qi
 */
public class SensitiveAfterFilter extends AfterFilter {

  @Override
  public void writeAfter(Object object) {
    SensitiveContext.removeConfigContext();
  }

}
