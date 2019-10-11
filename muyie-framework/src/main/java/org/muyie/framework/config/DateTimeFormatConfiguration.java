package org.muyie.framework.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configure the converters to use the ISO format for dates by default.
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter({MuyieProperties.class, JacksonProperties.class})
@Order(Ordered.LOWEST_PRECEDENCE)
public class DateTimeFormatConfiguration implements WebMvcConfigurer, InitializingBean {

  private final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  private final JacksonProperties jacksonProperties;

  public DateTimeFormatConfiguration(JacksonProperties jacksonProperties) {
    this.jacksonProperties = jacksonProperties;
  }

  @Override
  public void addFormatters(FormatterRegistry registry) {
    DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
    registrar.setUseIsoFormat(true);
    registrar.registerFormatters(registry);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    if (StringUtils.isBlank(jacksonProperties.getDateFormat())) {
      jacksonProperties.setDateFormat(DEFAULT_DATE_FORMAT);
    }
  }

}
