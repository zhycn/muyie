package com.muyie.dto;

import cn.hutool.core.util.StrUtil;
import com.muyie.exception.ExceptionUtil;
import lombok.Getter;
import org.apache.commons.lang3.time.DateUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.Objects;

import static cn.hutool.core.date.DatePattern.*;

/**
 * PageQuery：分页查询对象，内置了几个常用参数。
 *
 * @author larry.qi
 * @since 1.2.10
 */
public class PageQuery extends Query {
  private static final int DEFAULT_PAGE_NUM = 1;
  private static final int DEFAULT_PAGE_SIZE = 10;
  /**
   * 默认支持的日期格式：
   * <pre>
   * yyyy-MM-dd
   * yyyy-MM-dd HH:mm:ss
   * yyyyMMdd
   * yyyyMMddHHmmss
   * yyyy-MM-dd'T'HH:mm:ss
   * yyyy-MM-dd'T'HH:mm:ss'Z'
   * </pre>
   */
  private static final String[] DEFAULT_DATE_PATTERNS = new String[]{
    NORM_DATE_PATTERN, NORM_DATETIME_PATTERN, PURE_DATE_PATTERN, PURE_DATETIME_PATTERN, UTC_SIMPLE_PATTERN, UTC_PATTERN
  };

  /**
   * 当前页
   */
  @Min(1)
  private Integer pageNum = DEFAULT_PAGE_NUM;

  /**
   * 每页大小
   */
  @Min(1)
  @Max(500)
  private Integer pageSize = DEFAULT_PAGE_SIZE;

  /**
   * 查询字符串
   */
  @Getter
  private String q;

  /**
   * 状态查询字符串
   */
  @Getter
  private String state;

  /**
   * 开始时间
   */
  private String beginTime;

  /**
   * 结束时间
   */
  private String endTime;

  /**
   * 排序方式
   */
  @Getter
  private String sortBy;

  public static PageQuery of() {
    return new PageQuery();
  }

  public static PageQuery of(int pageNum, int pageSize) {
    return of().page(pageNum, pageSize);
  }

  /**
   * 获取当前页，最小值为1
   *
   * @return 当前页
   */
  public Integer getPageNum() {
    return (pageNum < 1) ? 1 : pageNum;
  }

  /**
   * 设置当前页，最小值为1
   *
   * @param pageNum 当前页，空值则默认为1
   * @return this
   */
  public PageQuery setPageNum(Integer pageNum) {
    this.pageNum = Objects.isNull(pageNum) ? DEFAULT_PAGE_NUM : pageNum;
    return this;
  }

  /**
   * 获取每页大小，默认值为10
   *
   * @return 每页大小
   */
  public Integer getPageSize() {
    return (pageSize < 1) ? DEFAULT_PAGE_SIZE : pageSize;
  }

  /**
   * 设置每页大小，最小值为10
   *
   * @param pageSize 每页大小，空值则默认为10
   * @return this
   */
  public PageQuery setPageSize(Integer pageSize) {
    this.pageSize = Objects.isNull(pageSize) ? DEFAULT_PAGE_SIZE : pageSize;
    return this;
  }

  /**
   * 设置查询字符串
   *
   * @param q 查询字符串
   * @return this
   */
  public PageQuery setQ(String q) {
    this.q = q;
    return this;
  }

  /**
   * 设置状态查询字符串
   *
   * @param state 状态查询字符串
   * @return this
   */
  public PageQuery setState(String state) {
    this.state = state;
    return this;
  }

  /**
   * 获取开始时间，支持的格式：
   * <pre>
   * yyyy-MM-dd
   * yyyy-MM-dd HH:mm:ss
   * yyyyMMdd
   * yyyyMMddHHmmss
   * yyyy-MM-dd'T'HH:mm:ss
   * yyyy-MM-dd'T'HH:mm:ss'Z'
   * </pre>
   *
   * @return 解析后的日期，开始时间未设置则返回null
   * @throws RuntimeException 日期格式解析错误则抛出异常
   */
  public Date getBeginTime() {
    if (StrUtil.isNotBlank(beginTime)) {
      try {
        return DateUtils.parseDate(beginTime, DEFAULT_DATE_PATTERNS);
      } catch (Exception e) {
        ExceptionUtil.validate(e).doThrow();
      }
    }
    return null;
  }

  /**
   * 设置开始时间，支持的格式：
   * <pre>
   * yyyy-MM-dd
   * yyyy-MM-dd HH:mm:ss
   * yyyyMMdd
   * yyyyMMddHHmmss
   * yyyy-MM-dd'T'HH:mm:ss
   * yyyy-MM-dd'T'HH:mm:ss'Z'
   * </pre>
   *
   * @param beginTime 开始时间
   * @return this
   */
  public PageQuery setBeginTime(String beginTime) {
    this.beginTime = beginTime;
    return this;
  }

  /**
   * 获取结束时间，支持的格式：
   * <pre>
   * yyyy-MM-dd
   * yyyy-MM-dd HH:mm:ss
   * yyyyMMdd
   * yyyyMMddHHmmss
   * yyyy-MM-dd'T'HH:mm:ss
   * yyyy-MM-dd'T'HH:mm:ss'Z'
   * </pre>
   *
   * @return 解析后的日期，结束时间未设置则返回null
   * @throws RuntimeException 日期格式解析错误则抛出异常
   */
  public Date getEndTime() {
    if (StrUtil.isNotBlank(endTime)) {
      try {
        return DateUtils.parseDate(endTime, DEFAULT_DATE_PATTERNS);
      } catch (Exception e) {
        ExceptionUtil.validate(e).doThrow();
      }
    }
    return null;
  }

  /**
   * 设置结束时间，支持的格式：
   * <pre>
   * yyyy-MM-dd
   * yyyy-MM-dd HH:mm:ss
   * yyyyMMdd
   * yyyyMMddHHmmss
   * yyyy-MM-dd'T'HH:mm:ss
   * yyyy-MM-dd'T'HH:mm:ss'Z'
   * </pre>
   *
   * @param endTime 结束时间
   * @return this
   */
  public PageQuery setEndTime(String endTime) {
    this.endTime = endTime;
    return this;
  }

  /**
   * 设置排序方式
   *
   * @param sortBy 排序方式
   * @return this
   */
  public PageQuery setSortBy(String sortBy) {
    this.sortBy = sortBy;
    return this;
  }

  /**
   * 设置分页参数
   *
   * @param pageNum  当前页，最小值为1
   * @param pageSize 每页大小，最小值为1
   * @return this
   */
  public PageQuery page(int pageNum, int pageSize) {
    this.setPageNum(pageNum);
    this.setPageSize(pageSize);
    return this;
  }

}
