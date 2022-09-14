package com.muyie.dto;

import com.muyie.exception.AssertUtil;
import com.muyie.exception.ValidationException;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.validator.constraints.Range;

import java.util.Date;
import java.util.Objects;

import javax.validation.constraints.Min;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_PATTERN;
import static cn.hutool.core.date.DatePattern.NORM_DATE_PATTERN;
import static cn.hutool.core.date.DatePattern.PURE_DATETIME_PATTERN;
import static cn.hutool.core.date.DatePattern.PURE_DATE_PATTERN;
import static cn.hutool.core.date.DatePattern.UTC_PATTERN;
import static cn.hutool.core.date.DatePattern.UTC_SIMPLE_PATTERN;

/**
 * PageQuery - 分页查询对象
 *
 * @author larry.qi
 * @since 1.2.10
 */
public class PageQuery extends Query {

  public static final String ASC = "ASC";
  public static final String DESC = "DESC";
  private static final long serialVersionUID = 1L;
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
  private static final String[] PARSE_PATTERNS = new String[]{
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
  @Range(min = 1, max = 500)
  private Integer pageSize = DEFAULT_PAGE_SIZE;

  /**
   * 查询字符串
   */
  private String q;

  /**
   * 状态查询字符串
   */
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
   * 排序字段
   */
  private String orderBy;

  /**
   * 排序方式
   */
  private String orderDirection = DESC;

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
   */
  public PageQuery setPageSize(Integer pageSize) {
    this.pageSize = Objects.isNull(pageSize) ? DEFAULT_PAGE_SIZE : pageSize;
    return this;
  }

  /**
   * 设置分页参数
   *
   * @param pageNum  当前页，最小值为1
   * @param pageSize 每页大小，最小值为1
   */
  public PageQuery page(int pageNum, int pageSize) {
    this.setPageNum(pageNum);
    this.setPageSize(pageSize);
    return this;
  }

  /**
   * 获取查询的起点位置
   *
   * @return 起点位置
   */
  public int getOffset() {
    return (getPageNum() - 1) * getPageSize();
  }

  /**
   * 获取查询字符串
   *
   * @return 查询字符串
   */
  public String getQ() {
    return q;
  }

  /**
   * 设置查询字符串
   *
   * @param q 查询字符串
   */
  public PageQuery setQ(String q) {
    this.q = q;
    return this;
  }

  /**
   * 获取状态查询字符串
   *
   * @return 状态查询字符串
   */
  public String getState() {
    return state;
  }

  /**
   * 设置状态查询字符串
   *
   * @param state 状态查询字符串
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
   * @throws ValidationException 日期格式解析错误则抛出异常
   */
  public Date getBeginTime() {
    if (StrUtil.isNotBlank(beginTime)) {
      try {
        return DateUtils.parseDate(beginTime, PARSE_PATTERNS);
      } catch (Exception e) {
        String detail = StrUtil.format("日期格式解析错误：beginTime={}, error={}", beginTime, e.getMessage());
        AssertUtil.validate(detail).doThrow();
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
   */
  public PageQuery setBeginTime(String beginTime) {
    this.beginTime = beginTime;
    return this;
  }

  /**
   * 如果开始时间不为空，则返回当天的开始时间
   *
   * @return 当天的开始时间，参考格式：yyyy-MM-dd 00:00:00
   */
  public Date getBeginTimeOfDay() {
    Date time = this.getBeginTime();
    return (Objects.nonNull(time)) ? DateUtil.beginOfDay(time) : null;
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
   * @throws ValidationException 日期格式解析错误则抛出异常
   */
  public Date getEndTime() {
    if (StrUtil.isNotBlank(endTime)) {
      try {
        return DateUtils.parseDate(endTime, PARSE_PATTERNS);
      } catch (Exception e) {
        String detail = StrUtil.format("日期格式解析错误：endTime={}, error={}", endTime, e.getMessage());
        AssertUtil.validate(detail).doThrow();
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
   */
  public PageQuery setEndTime(String endTime) {
    this.endTime = endTime;
    return this;
  }

  /**
   * 如果结束时间不为空，则返回当天的最后时间
   *
   * @return 当天的最后时间，参考格式：yyyy-MM-dd 23:59:59
   */
  public Date getEndTimeOfDay() {
    Date time = this.getEndTime();
    return (Objects.nonNull(time)) ? DateUtil.endOfDay(time) : null;
  }

  /**
   * 获取排序字段，使用时要判断字段是否正确
   *
   * @return 排序字段
   */
  public String getOrderBy() {
    return orderBy;
  }

  /**
   * 设置排序字段
   *
   * @param orderBy 排序字段
   */
  public PageQuery setOrderBy(String orderBy) {
    this.orderBy = orderBy;
    return this;
  }

  /**
   * 获取排序方式，支持ASC和DESC，默认值是DESC。
   *
   * @return 排序方式
   */
  public String getOrderDirection() {
    return orderDirection;
  }

  /**
   * 设置排序方式，支持ASC和DESC，默认值是DESC。
   *
   * @param orderDirection 排序方式，忽略大小写
   */
  public PageQuery setOrderDirection(String orderDirection) {
    if (ASC.equalsIgnoreCase(orderDirection) || DESC.equalsIgnoreCase(orderDirection)) {
      this.orderDirection = orderDirection.toUpperCase();
    }
    return this;
  }

  /**
   * 设置排序
   *
   * @param orderBy        排序字段
   * @param orderDirection 排序方式，忽略大小写
   */
  public PageQuery sort(String orderBy, String orderDirection) {
    this.setOrderBy(orderBy);
    this.setOrderDirection(orderDirection);
    return this;
  }

}
