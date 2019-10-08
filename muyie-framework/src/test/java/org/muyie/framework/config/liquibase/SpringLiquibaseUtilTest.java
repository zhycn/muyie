package org.muyie.framework.config.liquibase;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.Test;
import org.muyie.framework.config.liquibase.AsyncSpringLiquibase;
import org.muyie.framework.config.liquibase.SpringLiquibaseUtil;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.DataSourceClosingSpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class SpringLiquibaseUtilTest {

  @Test
  public void createSpringLiquibaseFromLiquibaseDataSource() {
    DataSource liquibaseDatasource =
        DataSourceBuilder.create().url("jdbc:h2:mem:liquibase").username("sa").build();
    LiquibaseProperties liquibaseProperties = null;
    DataSource normalDataSource = null;
    DataSourceProperties dataSourceProperties = null;

    SpringLiquibase liquibase = SpringLiquibaseUtil.createSpringLiquibase(liquibaseDatasource,
        liquibaseProperties, normalDataSource, dataSourceProperties);
    assertThat(liquibase).isNotInstanceOf(DataSourceClosingSpringLiquibase.class);
    assertThat(liquibase.getDataSource()).isEqualTo(liquibaseDatasource);
    assertThat(((HikariDataSource) liquibase.getDataSource()).getJdbcUrl())
        .isEqualTo("jdbc:h2:mem:liquibase");
    assertThat(((HikariDataSource) liquibase.getDataSource()).getUsername()).isEqualTo("sa");
    assertThat(((HikariDataSource) liquibase.getDataSource()).getPassword()).isNull();
  }

  @Test
  public void createSpringLiquibaseFromNormalDataSource() {
    DataSource liquibaseDatasource = null;
    LiquibaseProperties liquibaseProperties = new LiquibaseProperties();
    DataSource normalDataSource =
        DataSourceBuilder.create().url("jdbc:h2:mem:normal").username("sa").build();
    DataSourceProperties dataSourceProperties = null;

    SpringLiquibase liquibase = SpringLiquibaseUtil.createSpringLiquibase(liquibaseDatasource,
        liquibaseProperties, normalDataSource, dataSourceProperties);
    assertThat(liquibase).isNotInstanceOf(DataSourceClosingSpringLiquibase.class);
    assertThat(liquibase.getDataSource()).isEqualTo(normalDataSource);
    assertThat(((HikariDataSource) liquibase.getDataSource()).getJdbcUrl())
        .isEqualTo("jdbc:h2:mem:normal");
    assertThat(((HikariDataSource) liquibase.getDataSource()).getUsername()).isEqualTo("sa");
    assertThat(((HikariDataSource) liquibase.getDataSource()).getPassword()).isNull();
  }

  @Test
  public void createSpringLiquibaseFromLiquibaseProperties() {
    DataSource liquibaseDatasource = null;
    LiquibaseProperties liquibaseProperties = new LiquibaseProperties();
    liquibaseProperties.setUrl("jdbc:h2:mem:liquibase");
    liquibaseProperties.setUser("sa");
    DataSource normalDataSource = null;
    DataSourceProperties dataSourceProperties = new DataSourceProperties();
    dataSourceProperties.setPassword("password");

    SpringLiquibase liquibase = SpringLiquibaseUtil.createSpringLiquibase(liquibaseDatasource,
        liquibaseProperties, normalDataSource, dataSourceProperties);
    assertThat(liquibase).isInstanceOf(DataSourceClosingSpringLiquibase.class);
    assertThat(((HikariDataSource) liquibase.getDataSource()).getJdbcUrl())
        .isEqualTo("jdbc:h2:mem:liquibase");
    assertThat(((HikariDataSource) liquibase.getDataSource()).getUsername()).isEqualTo("sa");
    assertThat(((HikariDataSource) liquibase.getDataSource()).getPassword()).isEqualTo("password");
  }

  @Test
  public void createAsyncSpringLiquibaseFromLiquibaseDataSource() {
    DataSource liquibaseDatasource =
        DataSourceBuilder.create().url("jdbc:h2:mem:liquibase").username("sa").build();
    LiquibaseProperties liquibaseProperties = null;
    DataSource normalDataSource = null;
    DataSourceProperties dataSourceProperties = null;

    SpringLiquibase liquibase = SpringLiquibaseUtil.createAsyncSpringLiquibase(null, null,
        liquibaseDatasource, liquibaseProperties, normalDataSource, dataSourceProperties);
    assertThat(liquibase).isInstanceOf(AsyncSpringLiquibase.class);
    assertThat(liquibase.getDataSource()).isEqualTo(liquibaseDatasource);
    assertThat(((HikariDataSource) liquibase.getDataSource()).getJdbcUrl())
        .isEqualTo("jdbc:h2:mem:liquibase");
    assertThat(((HikariDataSource) liquibase.getDataSource()).getUsername()).isEqualTo("sa");
    assertThat(((HikariDataSource) liquibase.getDataSource()).getPassword()).isNull();
  }

  @Test
  public void createAsyncSpringLiquibaseFromNormalDataSource() {
    DataSource liquibaseDatasource = null;
    LiquibaseProperties liquibaseProperties = new LiquibaseProperties();
    DataSource normalDataSource =
        DataSourceBuilder.create().url("jdbc:h2:mem:normal").username("sa").build();
    DataSourceProperties dataSourceProperties = null;

    SpringLiquibase liquibase = SpringLiquibaseUtil.createAsyncSpringLiquibase(null, null,
        liquibaseDatasource, liquibaseProperties, normalDataSource, dataSourceProperties);
    assertThat(liquibase).isInstanceOf(AsyncSpringLiquibase.class);
    assertThat(liquibase.getDataSource()).isEqualTo(normalDataSource);
    assertThat(((HikariDataSource) liquibase.getDataSource()).getJdbcUrl())
        .isEqualTo("jdbc:h2:mem:normal");
    assertThat(((HikariDataSource) liquibase.getDataSource()).getUsername()).isEqualTo("sa");
    assertThat(((HikariDataSource) liquibase.getDataSource()).getPassword()).isNull();
  }

  @Test
  public void createAsyncSpringLiquibaseFromLiquibaseProperties() {
    DataSource liquibaseDatasource = null;
    LiquibaseProperties liquibaseProperties = new LiquibaseProperties();
    liquibaseProperties.setUrl("jdbc:h2:mem:liquibase");
    liquibaseProperties.setUser("sa");
    DataSource normalDataSource = null;
    DataSourceProperties dataSourceProperties = new DataSourceProperties();
    dataSourceProperties.setPassword("password");

    SpringLiquibase liquibase = SpringLiquibaseUtil.createAsyncSpringLiquibase(null, null,
        liquibaseDatasource, liquibaseProperties, normalDataSource, dataSourceProperties);
    assertThat(liquibase).isInstanceOf(AsyncSpringLiquibase.class);
    assertThat(((HikariDataSource) liquibase.getDataSource()).getJdbcUrl())
        .isEqualTo("jdbc:h2:mem:liquibase");
    assertThat(((HikariDataSource) liquibase.getDataSource()).getUsername()).isEqualTo("sa");
    assertThat(((HikariDataSource) liquibase.getDataSource()).getPassword()).isEqualTo("password");
  }

}
