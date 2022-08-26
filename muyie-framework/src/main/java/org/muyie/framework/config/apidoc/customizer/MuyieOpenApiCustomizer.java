package org.muyie.framework.config.apidoc.customizer;

import org.muyie.framework.config.MuyieProperties;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.core.Ordered;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * A OpenApi customizer to setup {@link OpenAPI} with MuYie settings.
 *
 * @author larry.qi
 * @since 1.2.6
 */
public class MuyieOpenApiCustomizer implements OpenApiCustomiser, Ordered {

  /**
   * The default order for the customizer.
   */
  public static final int DEFAULT_ORDER = 0;

  private int order = DEFAULT_ORDER;

  private final MuyieProperties.Swagger properties;

  /**
   * <p>Constructor for MuyieOpenApiCustomizer.</p>
   *
   * @param properties a {@link MuyieProperties.Swagger} object.
   */
  public MuyieOpenApiCustomizer(MuyieProperties.Swagger properties) {
    this.properties = properties;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void customise(OpenAPI openApi) {
    Contact contact = new Contact()
      .name(properties.getContactName())
      .url(properties.getContactUrl())
      .email(properties.getContactEmail());

    openApi.info(new Info()
      .contact(contact)
      .title(properties.getTitle())
      .description(properties.getDescription())
      .version(properties.getVersion())
      .termsOfService(properties.getTermsOfServiceUrl())
      .license(new License().name(properties.getLicense()).url(properties.getLicenseUrl()))
    );

  }

  /**
   * <p>Setter for the field <code>order</code>.</p>
   *
   * @param order an int.
   */
  public void setOrder(int order) {
    this.order = order;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getOrder() {
    return order;
  }
}
