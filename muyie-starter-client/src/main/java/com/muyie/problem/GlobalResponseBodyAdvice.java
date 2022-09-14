package com.muyie.problem;

import com.muyie.dto.Response;
import com.muyie.exception.ErrorCode;
import com.muyie.exception.ErrorCodeBuilder;
import com.muyie.exception.ErrorCodeDefaults;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.zalando.problem.Problem;

import java.util.Objects;
import java.util.Optional;

/**
 * 全局拦截器，对响应报文进行处理。
 *
 * @author larry.qi
 * @since 1.2.10
 */
@Configuration(proxyBeanMethods = false)
@RestControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {

  private static ErrorCode convert(final String statusCode, final String defaultMsg) {
    String code = "S0" + statusCode;
    final ErrorCodeDefaults[] list = ErrorCodeDefaults.values();
    for (final ErrorCodeDefaults errorCodeDefault : list) {
      if (StringUtils.equals(errorCodeDefault.getCode(), code)) {
        return errorCodeDefault;
      }
    }
    return ErrorCodeBuilder.of(code, defaultMsg);
  }

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
      final String statusCode = String.valueOf(Objects.requireNonNull(problem.getStatus()).getStatusCode());
      return Response.of(convert(statusCode, problem.getTitle()));
    }

    // Handle Response headers
    if (body instanceof Response) {
      final Response result = (Response) body;
      Optional.ofNullable(result.getHeaders()).ifPresent(headers -> response.getHeaders().addAll(headers));
      return result;
    }

    return body;
  }

}
