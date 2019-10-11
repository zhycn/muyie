package org.muyie.framework.config.apidoc.customizer;

import java.util.List;

import org.muyie.framework.config.MuyieProperties;
import org.springframework.core.annotation.Order;

import com.google.common.collect.Lists;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * A authorization swagger customizer to setup
 * {@link springfox.documentation.spring.web.plugins.Docket} with MuYie settings.
 */
@Order(1)
public class AuthorizationSwaggerCustomizer implements SwaggerCustomizer {

  private final MuyieProperties.Swagger properties;

  /**
   * <p>
   * Constructor for AuthorizationSwaggerCustomizer.
   * </p>
   *
   * @param properties a {@link org.muyie.framework.config.MuyieProperties.Swagger} object.
   */
  public AuthorizationSwaggerCustomizer(MuyieProperties.Swagger properties) {
    this.properties = properties;
  }

  /** {@inheritDoc} */
  @Override
  public void customize(Docket docket) {
    if (properties.getAuthorization().isEnabled()) {
      Parameter parameter = new ParameterBuilder().name(properties.getAuthorization().getName())
          .description(properties.getAuthorization().getDescription())
          .defaultValue(properties.getAuthorization().getDefaultValue())
          .required(properties.getAuthorization().isRequired()).modelRef(new ModelRef("string"))
          .parameterType(properties.getAuthorization().getParamType())
          .pattern(properties.getAuthorization().getPattern()).build();
      List<Parameter> parameters = Lists.newArrayList();
      parameters.add(parameter);
      docket.globalOperationParameters(parameters);
    }
  }

}
