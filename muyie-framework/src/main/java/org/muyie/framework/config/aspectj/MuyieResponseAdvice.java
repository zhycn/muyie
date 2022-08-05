package org.muyie.framework.config.aspectj;

import org.apache.commons.lang3.StringUtils;
import org.muyie.framework.config.MuyieProperties;
import org.muyie.framework.context.CodeBuilder;
import org.muyie.framework.context.CodeDefaults;
import org.muyie.framework.context.ICode;
import org.muyie.framework.context.IResult;
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

  /**
   * HTTP错误码转换
   *
   * @param code       HTTP错误码
   * @param defaultMsg 错误信息
   * @return 构建新的错误码
   */
  private static ICode convert(final String code, final String defaultMsg) {
    final CodeDefaults[] list = CodeDefaults.values();
    for (final CodeDefaults defaults : list) {
      if (StringUtils.contains(defaults.getCode(), code)) {
        return defaults;
      }
    }
    return CodeBuilder.of(StringUtils.leftPad(code, 5, "0"), defaultMsg);
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
      final String statusCode = String.valueOf(problem.getStatus().getStatusCode());
      return IResult.fail(convert(statusCode, problem.getTitle()));
    }

    // Handle REST Response headers
    if (body instanceof IResult) {
      final IResult result = (IResult) body;
      response.getHeaders().addAll(result.getHeaders());
      return result;
    }

    return body;
  }

}
