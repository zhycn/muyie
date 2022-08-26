package com.muyie.framework.config.apidoc;

import com.muyie.framework.config.apidoc.customizer.MuyieOpenApiCustomizer;
import com.muyie.framework.properties.MuyieProperties;

import org.springdoc.core.SpringDocConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * OpenAPI configuration.
 * <p>
 * Warning! When having a lot of REST endpoints, OpenApi can become a performance issue. In that
 * case, you can use the "springdoc.api-docs.enabled=false" Spring properties, so that this bean is
 * ignored.
 *
 * @author larry.qi
 * @since 1.2.6
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(OpenAPI.class)
@AutoConfigureBefore(SpringDocConfiguration.class)
@AutoConfigureAfter(MuyieProperties.class)
public class MuyieOpenApiAutoConfiguration {

  private final MuyieProperties.ApiDocs properties;

  /**
   * <p>Constructor for MuyieOpenApiAutoConfiguration.</p>
   *
   * @param properties a {@link MuyieProperties.ApiDocs} object.
   */
  public MuyieOpenApiAutoConfiguration(MuyieProperties.ApiDocs properties) {
    this.properties = properties;
  }

  @Bean
  @ConditionalOnMissingBean
  public OpenAPI createOpenApi() {
    OpenAPI openApi = new OpenAPI()
      .schemaRequirement(HttpHeaders.AUTHORIZATION,
        new SecurityScheme()
          .type(SecurityScheme.Type.APIKEY)
          .name(HttpHeaders.AUTHORIZATION)
          .in(SecurityScheme.In.HEADER)
      ).addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION));
    new MuyieOpenApiCustomizer(properties).customise(openApi);
    return openApi;
  }

}
