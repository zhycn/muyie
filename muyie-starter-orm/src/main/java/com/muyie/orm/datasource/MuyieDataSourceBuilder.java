package com.muyie.orm.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * @author larry.qi
 * @since 1.2.11
 */
public class MuyieDataSourceBuilder {

  @SuppressWarnings("unchecked")
  protected static <T> T createHikariDataSource(DataSourceProperties properties, Class<? extends DataSource> type) {
    return (T) properties.initializeDataSourceBuilder().type(type).build();
  }

  /**
   * 创建 HikariDataSource 数据源
   *
   * @param properties 参数
   * @return 数据源
   */
  public static HikariDataSource createHikariDataSource(DataSourceProperties properties) {
    HikariDataSource dataSource = createHikariDataSource(properties, HikariDataSource.class);
    if (StringUtils.hasText(properties.getName())) {
      dataSource.setPoolName(properties.getName());
    }
    return dataSource;
  }

  /**
   * 创建 DruidDataSource 数据源
   *
   * @return 数据源
   */
  public static DruidDataSource createDruidDataSource() {
    return DruidDataSourceBuilder.create().build();
  }

}
