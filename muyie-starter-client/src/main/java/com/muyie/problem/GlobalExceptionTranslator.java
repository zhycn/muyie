package com.muyie.problem;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures. The
 * error response follows RFC7807 - Problem Details for HTTP APIs (<a
 * href="https://tools.ietf.org/html/rfc7807">RFC7807</a>)
 *
 * @author larry.qi
 * @since 1.2.10
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(ProblemHandling.class)
@RestControllerAdvice
public class GlobalExceptionTranslator implements ProblemHandling {

}
