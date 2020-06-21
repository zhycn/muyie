package org.muyie.framework.security.ssl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.xnio.OptionMap;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;

public class UndertowSSLConfigurationTest {

  @Test
  public void testUndertowSSLConfigurationOK() {
    // Prepare
    final UndertowServletWebServerFactory undertowServletWebServerFactory = new UndertowServletWebServerFactory();

    // Execute
    new UndertowSSLConfiguration(undertowServletWebServerFactory);

    // Verify
    final Undertow.Builder builder = Undertow.builder();
    undertowServletWebServerFactory.getBuilderCustomizers().forEach(c -> c.customize(builder));
    final OptionMap.Builder serverOptions = (OptionMap.Builder) ReflectionTestUtils.getField(builder, "socketOptions");
    assertThat(undertowServletWebServerFactory).isNotNull();
    assertThat(serverOptions.getMap().get(UndertowOptions.SSL_USER_CIPHER_SUITES_ORDER)).isTrue();
  }

}
