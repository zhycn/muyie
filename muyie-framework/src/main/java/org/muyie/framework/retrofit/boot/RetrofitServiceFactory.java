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
package org.muyie.framework.retrofit.boot;

import org.muyie.framework.retrofit.context.RetrofitContext;

import retrofit2.Retrofit;

public class RetrofitServiceFactory {

  private final RetrofitContext retrofitContext;

  public RetrofitServiceFactory(RetrofitContext retrofitContext) {
    this.retrofitContext = retrofitContext;
  }

  public <T> T createServiceInstance(Class<T> serviceClass, String identity) {
    Retrofit retrofit = getConfiguredRetrofit(identity);
    return retrofit.create(serviceClass);
  }

  private Retrofit getConfiguredRetrofit(String identity) {
    return retrofitContext.find(identity).orElseThrow(() -> new RuntimeException(
      "Cannot obtain [" + identity + "] Retrofit in your application configuration file."));
  }

}
