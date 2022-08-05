/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.muyie.framework.retrofit.autoconfigure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.muyie.framework.config.MuyieProperties;
import org.muyie.framework.retrofit.context.DefaultRetrofitContext;
import org.muyie.framework.retrofit.context.RetrofitContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.util.List;
import java.util.Map;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@ConditionalOnClass(Retrofit.class)
@AutoConfigureAfter({MuyieProperties.class})
public class RetrofitAutoConfiguration {

  private final List<Converter.Factory> converterFactories;
  private final OkHttpClient okHttpClient;
  private final MuyieProperties muyieProperties;

  @Autowired
  public RetrofitAutoConfiguration(List<Converter.Factory> converterFactories,
                                   OkHttpClient okHttpClient, MuyieProperties muyieProperties) {
    this.converterFactories = converterFactories;
    this.okHttpClient = okHttpClient;
    this.muyieProperties = muyieProperties;
    this.checkConfiguredUrl(muyieProperties);
  }

  @Bean
  @ConditionalOnMissingBean
  public RetrofitContext retrofitContext() {
    Builder builder = new Builder().validateEagerly(true);
    converterFactories.forEach(builder::addConverterFactory);

    if (okHttpClient != null) {
      builder.client(okHttpClient);
    }

    RetrofitContext context = new DefaultRetrofitContext();
    Map<String, String> endpoints = muyieProperties.getRetrofit().getEndpoints();

    endpoints.keySet().forEach(key -> {
      context.register(key, builder.baseUrl(endpoints.get(key)).build());
    });

    return context;
  }

  private void checkConfiguredUrl(MuyieProperties properties) {
    Map<String, String> endpoints = properties.getRetrofit().getEndpoints();
    endpoints.keySet().forEach(key -> {
      String url = endpoints.get(key);
      Assert.isTrue(ResourceUtils.isUrl(url), url + " is not a valid url");
    });
  }

  @Configuration
  @ConditionalOnClass(OkHttpClient.class)
  public static class OkHttpClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ConnectionPool connectionPool(MuyieProperties properties) {
      MuyieProperties.Retrofit retrofit = properties.getRetrofit();
      return new ConnectionPool(retrofit.getMaxIdle(), retrofit.getKeepAlive(), MINUTES);
    }

    @Bean
    @ConditionalOnMissingBean
    public OkHttpClient okHttpClient(MuyieProperties properties, ConnectionPool connectionPool) {
      MuyieProperties.Retrofit retrofit = properties.getRetrofit();
      OkHttpClient.Builder builder =
        new OkHttpClient.Builder().readTimeout(retrofit.getReadTimeout(), MILLISECONDS)
          .writeTimeout(retrofit.getWriteTimeout(), MILLISECONDS)
          .connectTimeout(retrofit.getConnectTimeout(), MILLISECONDS)
          .connectionPool(connectionPool);
      return builder.build();
    }
  }

  @Configuration
  @ConditionalOnClass(JacksonConverterFactory.class)
  public static class JacksonConverterFactoryConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper mapper() {
      ObjectMapper mapper = new ObjectMapper();
      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      mapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);
      return mapper;
    }

    @Bean
    @ConditionalOnMissingBean
    public JacksonConverterFactory jacksonConverterFactory(ObjectMapper mapper) {
      mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
      return JacksonConverterFactory.create(mapper);
    }
  }

}
