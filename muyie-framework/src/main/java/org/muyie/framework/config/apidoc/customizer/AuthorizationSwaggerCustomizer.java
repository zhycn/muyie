package org.muyie.framework.config.apidoc.customizer;

import java.util.List;

import org.muyie.framework.config.MuyieProperties;
import org.springframework.core.annotation.Order;

import com.google.common.collect.Lists;

import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * A authorization swagger customizer to setup
 * {@link springfox.documentation.spring.web.plugins.Docket} with MuYie
 * settings.
 */
@Order(1)
public class AuthorizationSwaggerCustomizer implements SwaggerCustomizer {

  private final MuyieProperties.Swagger properties;

  /**
   * <p>
   * Constructor for AuthorizationSwaggerCustomizer.
   * </p>
   *
   * @param properties a
   *                   {@link org.muyie.framework.config.MuyieProperties.Swagger}
   *                   object.
   */
  public AuthorizationSwaggerCustomizer(final MuyieProperties.Swagger properties) {
    this.properties = properties;
  }

  /** {@inheritDoc} */
  @Override
  public void customize(final Docket docket) {
    if (properties.getAuthorization().isEnabled()) {
      final RequestParameter parameter = new RequestParameterBuilder().name(properties.getAuthorization().getName())
          .description(properties.getAuthorization().getDescription())
          .required(properties.getAuthorization().isRequired()).in(ParameterType.HEADER)
          .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING))).build();

      final List<RequestParameter> globalRequestParameters = Lists.newArrayList();
      globalRequestParameters.add(parameter);
      docket.globalRequestParameters(globalRequestParameters);
    }
  }

}
