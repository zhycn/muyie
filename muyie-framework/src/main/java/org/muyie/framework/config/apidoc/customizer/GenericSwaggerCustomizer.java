package org.muyie.framework.config.apidoc.customizer;

import org.muyie.framework.config.MuyieProperties;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * A generic swagger customizer to setup {@link springfox.documentation.spring.web.plugins.Docket}
 * with MuYie settings.
 */
@Order(0)
public class GenericSwaggerCustomizer implements SwaggerCustomizer {

  private final MuyieProperties.Swagger properties;

  /**
   * <p>
   * Constructor for GenericSwaggerCustomizer.
   * </p>
   *
   * @param properties a {@link MuyieProperties.Swagger} object.
   */
  public GenericSwaggerCustomizer(final MuyieProperties.Swagger properties) {
    this.properties = properties;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void customize(final Docket docket) {
    final Contact contact = new Contact(properties.getContactName(), properties.getContactUrl(),
      properties.getContactEmail());

    final ApiInfo apiInfo = new ApiInfo(properties.getTitle(), properties.getDescription(), properties.getVersion(),
      properties.getTermsOfServiceUrl(), contact, properties.getLicense(), properties.getLicenseUrl(),
      new ArrayList<>());

    docket.host(properties.getHost()).protocols(new HashSet<>(Arrays.asList(properties.getProtocols())))
      .apiInfo(apiInfo).useDefaultResponseMessages(properties.isUseDefaultResponseMessages()).forCodeGeneration(true)
      .directModelSubstitute(ByteBuffer.class, String.class).genericModelSubstitutes(ResponseEntity.class).select()
      .paths(regex(properties.getDefaultIncludePattern())).build();
  }

}
