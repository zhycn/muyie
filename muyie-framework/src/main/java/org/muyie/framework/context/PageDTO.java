package org.muyie.framework.context;

import com.google.common.base.Throwables;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import javax.validation.constraints.Min;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_PATTERN;
import static cn.hutool.core.date.DatePattern.NORM_DATE_PATTERN;

/**
 * 分页接口请求参数基类
 */
public class PageDTO extends BaseDTO {

  private static final Logger log = LoggerFactory.getLogger(PageDTO.class);

  private static final long serialVersionUID = 1L;

  // 简单查询字符串
  private String q;

  @Min(1)
  private Integer page = 1; // 当前页

  @Min(1)
  private Integer size = 10; // 每页大小

  private String startDate; // 开始时间

  private String endDate; // 结束时间

  private String sort; // 排序

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public String getQ() {
    return q;
  }

  public void setQ(String q) {
    this.q = q;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  /**
   * 解析传入字符串格式的日期
   *
   * @return 日期
   */
  public Date formatEndDate() {
    try {
      if (StringUtils.isNotBlank(this.getEndDate())) {
        Date eDate = DateUtils.parseDate(this.getEndDate(), NORM_DATE_PATTERN, NORM_DATETIME_PATTERN);
        if (StringUtils.equals(startDate, endDate)) {
          return DateUtils.addDays(eDate, 1);
        }
        return eDate;
      }
    } catch (Exception e) {
      log.warn("解析日期出错：{}", Throwables.getStackTraceAsString(e));
    }
    return null;
  }

  /**
   * 将日期转换为通用格式
   *
   * @return 日期格式的字符串
   */
  public String formatEndDateStr() {
    Date date = formatEndDate();
    if (date == null) {
      return "";
    }
    return DateFormatUtils.format(date, NORM_DATETIME_PATTERN);
  }

  /**
   * 解析传入字符串格式的日期
   *
   * @return 日期
   */
  public Date formatStartDate() {
    try {
      if (StringUtils.isNotBlank(this.getStartDate())) {
        return DateUtils.parseDate(this.getStartDate(), NORM_DATE_PATTERN, NORM_DATETIME_PATTERN);
      }
    } catch (Exception e) {
      log.warn("解析日期出错：{}", Throwables.getStackTraceAsString(e));
    }
    return null;
  }

  /**
   * 将日期转换为通用格式
   *
   * @return 日期格式的字符串
   */
  public String formatStartDateStr() {
    Date date = formatStartDate();
    if (date == null) {
      return "";
    }
    return DateFormatUtils.format(date, NORM_DATETIME_PATTERN);
  }

}
