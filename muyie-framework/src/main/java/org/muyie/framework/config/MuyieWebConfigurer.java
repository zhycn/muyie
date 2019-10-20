package org.muyie.framework.config;

import java.nio.charset.StandardCharsets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(MuyieProperties.class)
@Import({
  DateTimeFormatConfiguration.class, 
  JacksonConfiguration.class, 
  LocaleConfiguration.class
})
public class MuyieWebConfigurer
    implements
      ServletContextInitializer,
      WebServerFactoryCustomizer<WebServerFactory> {

  private static final Logger log = LoggerFactory.getLogger(MuyieWebConfigurer.class);

  private final Environment env;

  private final MuyieProperties muyieProperties;

  public MuyieWebConfigurer(Environment env, MuyieProperties muyieProperties) {
    this.env = env;
    this.muyieProperties = muyieProperties;
  }

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    if (env.getActiveProfiles().length != 0) {
      log.info("Web application configuration, using profiles: {}",
          (Object[]) env.getActiveProfiles());
    }
  }

  /**
   * Customize the Servlet engine: Mime types, the document root, the cache.
   */
  @Override
  public void customize(WebServerFactory server) {
    setMimeMappings(server);
  }

  private void setMimeMappings(WebServerFactory server) {
    if (server instanceof ConfigurableServletWebServerFactory) {
      MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
      String mimeType =
          MediaType.TEXT_HTML_VALUE + ";charset=" + StandardCharsets.UTF_8.name().toLowerCase();
      // IE issue, see https://github.com/jhipster/generator-jhipster/pull/711
      mappings.add("html", mimeType);
      // CloudFoundry issue, see https://github.com/cloudfoundry/gorouter/issues/64
      mappings.add("json", mimeType);
      ConfigurableServletWebServerFactory servletWebServer =
          (ConfigurableServletWebServerFactory) server;
      servletWebServer.setMimeMappings(mappings);
    }
  }

  @Bean
  @ConditionalOnMissingBean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = muyieProperties.getCors();
    if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
      log.debug("Registering CORS filter");
      source.registerCorsConfiguration("/api/**", config);
      source.registerCorsConfiguration("/management/**", config);
      source.registerCorsConfiguration("/v2/api-docs", config);
    }
    return new CorsFilter(source);
  }

}
