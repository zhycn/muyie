package com.muyie.framework.config;

/**
 * MuyieDefaults constant.
 *
 * @author larry.qi
 * @since 1.2.6
 */
public interface MuyieDefaults {

  interface ApiDocs {

    String TITLE = "Application API";
    String DESCRIPTION = "API Documentation";
    String VERSION = "0.0.1";
    String TERMS_OF_SERVICE_URL = null;
    String CONTACT_NAME = null;
    String CONTACT_URL = null;
    String CONTACT_EMAIL = null;
    String LICENSE = null;
    String LICENSE_URL = null;
  }

  interface StopWatch {

    int SLOW_METHOD_MILLIS = -1;

  }

}
