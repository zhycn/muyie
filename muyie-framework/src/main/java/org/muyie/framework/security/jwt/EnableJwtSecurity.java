package org.muyie.framework.security.jwt;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enable JWT for Spring security and dependency on spring-boot-starter-security.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(JwtSecurityConfiguration.class)
@Documented
public @interface EnableJwtSecurity {

}
