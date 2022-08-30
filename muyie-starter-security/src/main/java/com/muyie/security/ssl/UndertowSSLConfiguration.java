package com.muyie.security.ssl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Configuration;

import io.undertow.UndertowOptions;
import lombok.extern.slf4j.Slf4j;

/**
 * SSL configuration for Undertow.
 * <p>
 * SSL_USER_CIPHER_SUITES_ORDER : It will force the cipher suite defined by the user, allowing to
 * achieve perfect forward secrecy. This can only be activated with HTTPS and a cipher suite defined
 * by the user (server.ssl.ciphers).
 * <p>
 * Please note that when using MuYie, you can use the `server.ssl.ciphers` property that is
 * available in your `application-tls.yml` file, and which is ready to work with this
 * configuration.
 *
 * @author larry.qi
 * @see <a
 * href="https://github.com/ssllabs/research/wiki/SSL-and-TLS-Deployment-Best-Practices#25-use-forward-secrecy"
 * target="_blank">More explanation on perfect forward secrecy</a>
 */
@Slf4j
@Configuration
@ConditionalOnBean(UndertowServletWebServerFactory.class)
@ConditionalOnClass(UndertowOptions.class)
@ConditionalOnProperty({"server.ssl.ciphers", "server.ssl.key-store"})
public class UndertowSSLConfiguration {

  private final UndertowServletWebServerFactory factory;

  /**
   * <p>Constructor for UndertowSSLConfiguration.</p>
   *
   * @param undertowServletWebServerFactory a
   *                                        {@link
   *                                        org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory}
   *                                        object.
   */
  public UndertowSSLConfiguration(UndertowServletWebServerFactory undertowServletWebServerFactory) {
    this.factory = undertowServletWebServerFactory;
    configuringUserCipherSuiteOrder();
  }

  private void configuringUserCipherSuiteOrder() {
    log.info("Configuring Undertow");
    log.info("Setting user cipher suite order to true");
    this.factory.addBuilderCustomizers(builder -> builder.setSocketOption(UndertowOptions
      .SSL_USER_CIPHER_SUITES_ORDER, Boolean.TRUE));
  }
}
