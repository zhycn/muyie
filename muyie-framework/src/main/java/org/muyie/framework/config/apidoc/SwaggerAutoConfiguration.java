package org.muyie.framework.config.apidoc;

import org.muyie.framework.config.MuyieProperties;
import org.muyie.framework.config.apidoc.customizer.MuyieSwaggerCustomizer;
import org.muyie.framework.config.apidoc.customizer.SwaggerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.DispatcherServlet;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.Servlet;
import java.nio.ByteBuffer;
import java.util.*;

import static org.muyie.framework.config.MuyieConstants.SPRING_PROFILE_SWAGGER;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Springfox Swagger configuration.
 * <p>
 * Warning! When having a lot of REST endpoints, Springfox can become a performance issue. In that
 * case, you can use the "no-swagger" Spring profile, so that this bean is ignored.
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({ApiInfo.class, BeanValidatorPluginsConfiguration.class, Servlet.class,
    DispatcherServlet.class, Docket.class})
@Profile(SPRING_PROFILE_SWAGGER)
@AutoConfigureAfter(MuyieProperties.class)
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerAutoConfiguration {

  static final String STARTING_MESSAGE = "Starting Swagger";
  static final String STARTED_MESSAGE = "Started Swagger in {} ms";
  static final String MANAGEMENT_TITLE_SUFFIX = "Management API";
  static final String MANAGEMENT_GROUP_NAME = "management";
  static final String MANAGEMENT_DESCRIPTION = "Management endpoints documentation";

  private final Logger log = LoggerFactory.getLogger(SwaggerAutoConfiguration.class);

  private final MuyieProperties.Swagger properties;

  /**
   * <p>
   * Constructor for SwaggerAutoConfiguration.
   * </p>
   *
   * @param muyieProperties a {@link org.muyie.framework.config.MuyieProperties} object.
   */
  public SwaggerAutoConfiguration(MuyieProperties muyieProperties) {
    this.properties = muyieProperties.getSwagger();
  }

  /**
   * Springfox configuration for the API Swagger docs.
   *
   * @param swaggerCustomizers Swagger customizers
   * @param alternateTypeRules alternate type rules
   * @return the Swagger Springfox configuration
   */
  @Bean
  @ConditionalOnMissingBean(name = "swaggerSpringfoxApiDocket")
  public Docket swaggerSpringfoxApiDocket(List<SwaggerCustomizer> swaggerCustomizers,
      ObjectProvider<AlternateTypeRule[]> alternateTypeRules) {
    log.debug(STARTING_MESSAGE);
    StopWatch watch = new StopWatch();
    watch.start();

    Docket docket = createDocket();

    // Apply all SwaggerCustomizers orderly.
    swaggerCustomizers.forEach(customizer -> customizer.customize(docket));

    // Add all AlternateTypeRules if available in spring bean factory.
    // Also you can add your rules in a customizer bean above.
    Optional.ofNullable(alternateTypeRules.getIfAvailable()).ifPresent(docket::alternateTypeRules);

    watch.stop();
    log.debug(STARTED_MESSAGE, watch.getTotalTimeMillis());
    return docket;
  }

  /**
   * MuYie Swagger Customizer
   *
   * @return the Swagger Customizer of MuYie
   */
  @Bean
  public MuyieSwaggerCustomizer muyieSwaggerCustomizer() {
    return new MuyieSwaggerCustomizer(properties);
  }

  /**
   * Springfox configuration for the management endpoints (actuator) Swagger docs.
   *
   * @param appName the application name
   * @param managementContextPath the path to access management endpoints
   * @return the Swagger Springfox configuration
   */
  @Bean
  @ConditionalOnClass(name = "org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties")
  @ConditionalOnProperty("management.endpoints.web.base-path")
  @ConditionalOnExpression("'${management.endpoints.web.base-path}'.length() > 0")
  @ConditionalOnMissingBean(name = "swaggerSpringfoxManagementDocket")
  public Docket swaggerSpringfoxManagementDocket(
      @Value("${spring.application.name:application}") String appName,
      @Value("${management.endpoints.web.base-path}") String managementContextPath) {

    ApiInfo apiInfo = new ApiInfo(StringUtils.capitalize(appName) + " " + MANAGEMENT_TITLE_SUFFIX,
        MANAGEMENT_DESCRIPTION, properties.getVersion(), "", ApiInfo.DEFAULT_CONTACT, "", "",
        new ArrayList<>());

    return createDocket().apiInfo(apiInfo)
        .useDefaultResponseMessages(properties.isUseDefaultResponseMessages())
        .groupName(MANAGEMENT_GROUP_NAME).host(properties.getHost())
        .protocols(new HashSet<>(Arrays.asList(properties.getProtocols()))).forCodeGeneration(true)
        .directModelSubstitute(ByteBuffer.class, String.class)
        .genericModelSubstitutes(ResponseEntity.class).ignoredParameterTypes(Pageable.class)
        .select().paths(regex(managementContextPath + ".*")).build();
  }

  /**
   * <p>
   * createDocket.
   * </p>
   *
   * @return a {@link springfox.documentation.spring.web.plugins.Docket} object.
   */
  protected Docket createDocket() {
    return new Docket(DocumentationType.SWAGGER_2);
  }

}
