package org.muyie.framework.logback;

import org.apache.commons.lang3.StringUtils;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import cn.hutool.core.net.NetUtil;

/**
 * 获取本机IP地址
 */
public class IPConverter extends ClassicConverter {

  private static final String IP;

  static {
    IP = StringUtils.defaultIfBlank(NetUtil.getLocalhostStr(), NetUtil.getLocalHostName());
  }

  @Override
  public String convert(ILoggingEvent event) {
    return IP;
  }

}
