package com.muyie.security.jwt;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enable JWT for Spring security and dependency on spring-boot-starter-security.
 *
 * @author larry.qi
 * @since 2.7.13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(JwtSecurityConfiguration.class)
@Documented
public @interface EnableJwtSecurity {

}
