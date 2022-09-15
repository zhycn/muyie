package com.muyie.problem;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;

/**
 * 统一异常处理配置类
 *
 * @author larry.qi
 * @since 1.2.10
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
@ConditionalOnProperty(value = "muyie.exception.enabled", havingValue = "true", matchIfMissing = true)
@Import({GlobalResponseBodyAdvice.class, GlobalExceptionTranslator.class, GlobalExceptionHandler.class})
public class MuyieExceptionAutoConfiguration {

}
