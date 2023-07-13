package com.muyie.mybatis.datasource;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.util.StringUtils;

import lombok.experimental.UtilityClass;

/**
 * @author larry.qi
 * @since 1.2.11
 */
@UtilityClass
public class MuyieDataSourceBuilder {

  /**
   * 创建 HikariDataSource 数据源
   *
   * @param properties 参数
   * @return 数据源
   */
  public static HikariDataSource createHikariDataSource(DataSourceProperties properties) {
    HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    if (StringUtils.hasText(properties.getName())) {
      dataSource.setPoolName(properties.getName());
    }
    return dataSource;
  }
}
