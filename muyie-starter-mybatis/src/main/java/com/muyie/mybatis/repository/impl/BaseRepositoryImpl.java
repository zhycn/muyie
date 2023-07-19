package com.muyie.mybatis.repository.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * MyBatis Plus 提供的 Service CRUD 接口
 *
 * @param <T> 实体对象
 * @author larry
 * @since 2.7.13
 */
public class BaseRepositoryImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {
}
