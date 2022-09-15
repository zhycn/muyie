package com.muyie.orm.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 封装的数据仓库接口
 *
 * @param <T>  实体对象
 * @param <ID> 主键
 * @author larry.qi
 * @since 1.2.11
 */
@NoRepositoryBean
public interface JpaRepositoryWrapper<T, ID>
  extends
  JpaRepository<T, ID>,
  JpaSpecificationExecutor<T> {

}
