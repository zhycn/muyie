package org.muyie.framework.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitClient: A HTTP client based-retrofit
 * 
 * @author zhycn
 * @since 1.0.4 2020-01-14
 */
public class RetrofitClient {

  public static <T> T call(String baseUrl, Class<T> service) {
    return new Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient())
        .addConverterFactory(GsonConverterFactory.create()).build().create(service);
  }

  public static <T> T call(String baseUrl, Class<T> service, OkHttpClient okHttpClient) {
    return new Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build().create(service);
  }

  private static OkHttpClient okHttpClient() {
    return new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS)
        .connectionPool(new ConnectionPool(32, 5, TimeUnit.SECONDS)).build();
  }

}
