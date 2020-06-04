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
  public boolean supports(MethodParameter returnType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType,
      MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request, ServerHttpResponse response) {

    // Handle Problem Exception
    if (body instanceof Problem) {
      Problem problem = (Problem) body;
      String statusCode = String.valueOf(problem.getStatus().getStatusCode());
      return Response.fail(convert(statusCode, problem.getTitle()));
    }

    // Handle REST Response headers
    if (body instanceof Response) {
      Response resp = (Response) body;
      response.getHeaders().addAll(resp.getHeaders());
      return resp;
    }

    return body;
  }

  private static ResponseCode convert(String code, String defaultMsg) {
    List<ResponseCodeDefaults> list = Arrays.asList(ResponseCodeDefaults.values());
    for (ResponseCodeDefaults defaults : list) {
      if (StringUtils.equals(defaults.getCode(), code)) {
        return defaults;
      }
    }
    return ResponseCodeBuilder.of(code, defaultMsg);
  }

}
