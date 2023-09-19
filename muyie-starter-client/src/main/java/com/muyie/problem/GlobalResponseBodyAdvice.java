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
import org.springframework.lang.NonNull;
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

  private static final String CODE_HEADER = "S0";

  /**
   * 错误码转换，拼接前缀自动匹配错误码。
   *
   * @param statusCode 状态码
   * @param defaultMsg 错误信息
   * @return 错误码
   */
  private static ErrorCode convert(String statusCode, String defaultMsg) {
    String code = CODE_HEADER + statusCode;
    final ErrorCodeDefaults[] list = ErrorCodeDefaults.values();
    for (final ErrorCodeDefaults defaults : list) {
      if (StringUtils.equals(defaults.getCode(), code)) {
        return defaults;
      }
    }
    return ErrorCodeBuilder.of(code, defaultMsg);
  }

  @Override
  public boolean supports(@NonNull MethodParameter returnType,
                          @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType,
                                @NonNull MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {

    // Handle Problem Exception
    if (body instanceof Problem) {
      Problem problem = (Problem) body;
      String statusCode = String.valueOf(Objects.requireNonNull(problem.getStatus()).getStatusCode());
      return Response.of(convert(statusCode, problem.getTitle()));
    }

    // Handle Response headers
    if (body instanceof Response) {
      Response res = (Response) body;
      Optional.ofNullable(res.getHeaders()).ifPresent(headers -> response.getHeaders().addAll(headers));
      return res;
    }

    return body;
  }

}
