package org.muyie.framework.config.aspectj;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.muyie.framework.config.MuyieProperties;
import org.muyie.framework.context.Response;
import org.muyie.framework.context.ResponseCode;
import org.muyie.framework.context.ResponseCodeBuilder;
import org.muyie.framework.context.ResponseCodeDefaults;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.zalando.problem.Problem;

@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(MuyieProperties.class)
@RestControllerAdvice
public class MuyieResponseAdvice implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(final MethodParameter returnType,
      final Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(final Object body, final MethodParameter returnType,
      final MediaType selectedContentType, final Class<? extends HttpMessageConverter<?>> selectedConverterType,
      final ServerHttpRequest request, final ServerHttpResponse response) {

    // Handle Problem Exception
    if (body instanceof Problem) {
      final Problem problem = (Problem) body;
      final String statusCode = String.valueOf(problem.getStatus().getStatusCode());
      return Response.fail(convert(statusCode, problem.getTitle()));
    }

    // Handle REST Response headers
    if (body instanceof Response) {
      final Response resp = (Response) body;
      response.getHeaders().addAll(resp.getHeaders());
      return resp;
    }

    return body;
  }

  private static ResponseCode convert(final String code, final String defaultMsg) {
    final List<ResponseCodeDefaults> list = Arrays.asList(ResponseCodeDefaults.values());
    for (final ResponseCodeDefaults defaults : list) {
      if (StringUtils.equals(defaults.getCode(), code)) {
        return defaults;
      }
    }
    return ResponseCodeBuilder.of(code, defaultMsg);
  }

}
