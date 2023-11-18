package com.muyie.mybatis;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.muyie.mybatis.handler.AuditingDateMetaObjectHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis-Plus 插件配置
 *
 * @author larry.qi
 * @since 2.7.13
 */
@Configuration(proxyBeanMethods = false)
@EnableTransactionManagement
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class, MybatisPlusAutoConfiguration.class})
@Import(AuditingDateMetaObjectHandler.class)
public class MuyieMybatisPlusAutoConfiguration {

  /**
   * 默认的 MyBatis-Plus 插件配置，不满足需求可以在项目中重新配置，查看 MyBatis-Plus 官方文档了解更多详情。
   *
   * @return MybatisPlusInterceptor
   */
  @Bean
  @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
  public MybatisPlusInterceptor mybatisPlusInterceptor(TableNameHandler tableNameHandler) {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    // 分页插件（单页分页条数限制5000条 - 默认无限制）
    PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
    paginationInnerInterceptor.setMaxLimit(500L);
    interceptor.addInnerInterceptor(paginationInnerInterceptor);
    // 乐观锁插件（在实体类的字段上加上 @Version 注解）
    OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor = new OptimisticLockerInnerInterceptor();
    interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor);
    // 防全表更新与删除插件（针对 update 和 delete 语句，阻止恶意的全表更新和删除）
    BlockAttackInnerInterceptor blockAttackInnerInterceptor = new BlockAttackInnerInterceptor();
    interceptor.addInnerInterceptor(blockAttackInnerInterceptor);
    // 添加动态表名拦截器（有需要则可配置）
    DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
    dynamicTableNameInnerInterceptor.setTableNameHandler(tableNameHandler);
    interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
    return interceptor;
  }

  @Bean
  @ConditionalOnMissingBean(TableNameHandler.class)
  public TableNameHandler defaultTableNameHandler() {
    // 当使用动态表名时，需要重写此方法
    return (sql, tableName) -> tableName;
  }

}
