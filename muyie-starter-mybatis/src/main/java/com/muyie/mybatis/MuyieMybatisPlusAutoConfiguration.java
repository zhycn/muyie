package com.muyie.mybatis;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus 插件配置
 *
 * @author larry.qi
 * @since 1.2.11
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class, MybatisPlusAutoConfiguration.class})
public class MuyieMybatisPlusAutoConfiguration {

  /**
   * 默认的 MyBatis-Plus 插件配置，不满足需求可以在项目中重新配置，查看 MyBatis-Plus 官方文档了解更多详情。
   */
  @Bean
  @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    // 分页插件（单页分页条数限制5000条 - 默认无限制）
    PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
    paginationInnerInterceptor.setMaxLimit(5000L);
    interceptor.addInnerInterceptor(paginationInnerInterceptor);
    // 乐观锁插件（在实体类的字段上加上 @Version 注解）
    OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor = new OptimisticLockerInnerInterceptor();
    interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor);
    // 防全表更新与删除插件（针对 update 和 delete 语句，阻止恶意的全表更新和删除）
    BlockAttackInnerInterceptor blockAttackInnerInterceptor = new BlockAttackInnerInterceptor();
    interceptor.addInnerInterceptor(blockAttackInnerInterceptor);
    return interceptor;
  }

}
