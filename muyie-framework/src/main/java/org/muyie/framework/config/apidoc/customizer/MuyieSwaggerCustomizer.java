package org.muyie.framework.config.apidoc.customizer;

import org.muyie.framework.config.MuyieProperties;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * A swagger customizer to setup {@link springfox.documentation.spring.web.plugins.Docket} with
 * MuYie settings.
 */
public class MuyieSwaggerCustomizer implements SwaggerCustomizer, Ordered {

  /**
   * The default order for the customizer.
   */
  public static final int DEFAULT_ORDER = 0;

  private int order = DEFAULT_ORDER;

  private final MuyieProperties.Swagger properties;

  /**
   * <p>
   * Constructor for MuyieSwaggerCustomizer.
   * </p>
   *
   * @param properties a {@link org.muyie.framework.config.MuyieProperties.Swagger} object.
   */
  public MuyieSwaggerCustomizer(MuyieProperties.Swagger properties) {
    this.properties = properties;
  }

  /** {@inheritDoc} */
  @Override
  public void customize(Docket docket) {
    Contact contact = new Contact(properties.getContactName(), properties.getContactUrl(),
        properties.getContactEmail());

    ApiInfo apiInfo = new ApiInfo(properties.getTitle(), properties.getDescription(),
        properties.getVersion(), properties.getTermsOfServiceUrl(), contact,
        properties.getLicense(), properties.getLicenseUrl(), new ArrayList<>());

    docket.host(properties.getHost())
        .protocols(new HashSet<>(Arrays.asList(properties.getProtocols()))).apiInfo(apiInfo)
        .useDefaultResponseMessages(properties.isUseDefaultResponseMessages())
        .forCodeGeneration(true).directModelSubstitute(ByteBuffer.class, String.class)
        .genericModelSubstitutes(ResponseEntity.class).ignoredParameterTypes(Pageable.class)
        .select().paths(regex(properties.getDefaultIncludePattern())).build();
  }

  /**
   * <p>
   * Setter for the field <code>order</code>.
   * </p>
   *
   * @param order a int.
   */
  public void setOrder(int order) {
    this.order = order;
  }

  /** {@inheritDoc} */
  @Override
  public int getOrder() {
    return order;
  }
}
