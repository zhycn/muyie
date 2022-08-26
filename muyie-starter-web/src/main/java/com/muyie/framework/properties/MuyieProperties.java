package com.muyie.framework.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Properties specific to MuYie. Properties are configured in the application.yml file.
 *
 * @author larry.qi
 * @since 1.2.6
 */
@Data
@ConfigurationProperties(prefix = "muyie")
public class MuyieProperties {

  public final StopWatch stopWatch = new StopWatch();

  @Data
  public static class StopWatch {

    /**
     * 慢方法的阀值设置（全局）
     */
    private int slowMethodMillis = MuyieDefaults.StopWatch.SLOW_METHOD_MILLIS;

  }

  @Data
  public static class ApiDocs {

    private String title = MuyieDefaults.ApiDocs.TITLE;

    private String description = MuyieDefaults.ApiDocs.DESCRIPTION;

    private String version = MuyieDefaults.ApiDocs.VERSION;

    private String termsOfServiceUrl = MuyieDefaults.ApiDocs.TERMS_OF_SERVICE_URL;

    private String contactName = MuyieDefaults.ApiDocs.CONTACT_NAME;

    private String contactUrl = MuyieDefaults.ApiDocs.CONTACT_URL;

    private String contactEmail = MuyieDefaults.ApiDocs.CONTACT_EMAIL;

    private String license = MuyieDefaults.ApiDocs.LICENSE;

    private String licenseUrl = MuyieDefaults.ApiDocs.LICENSE_URL;

    private Server[] servers = {};

    @Data
    public static class Server {

      private String url;

      private String description;

    }

  }

}
