package com.muyie.web.logging;

import org.apache.commons.lang3.StringUtils;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import cn.hutool.core.net.NetUtil;

/**
 * 在日志文件中打印服务器 HOST 地址或名称，便于在集群环境中定位到日志所在机器。
 *
 * @author larry.qi
 * @since 1.2.6
 */
public class LogHostConverter extends ClassicConverter {

  private static final String HOST;

  static {
    HOST = StringUtils.defaultIfBlank(NetUtil.getLocalhostStr(), NetUtil.getLocalHostName());
  }

  @Override
  public String convert(ILoggingEvent event) {
    return HOST;
  }

}
