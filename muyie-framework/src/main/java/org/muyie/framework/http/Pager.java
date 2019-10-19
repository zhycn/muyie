package org.muyie.framework.http;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import javax.validation.constraints.Min;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import static org.muyie.framework.config.MuyieConstants.FORMAT_DATE;
import static org.muyie.framework.config.MuyieConstants.FORMAT_DATE_2;
import static org.muyie.framework.config.MuyieConstants.FORMAT_DATE_TIME;
import static org.muyie.framework.config.MuyieConstants.FORMAT_DATE_TIME_2;

public class Pager implements Serializable {

  private static final long serialVersionUID = 1L;

  private String q; // 模糊查询

  private String eq; // 精确查询

  private String state; // 状态查询

  private String st; // 开始时间

  private String et; // 结束时间

  private String f; // 指定时间格式，也可作其他用途

  private String sn; // 排序字段名称

  private String order; // 排序方式：ASC/DESC

  @Min(1)
  private Integer page = 1;

  @Min(1)
  private Integer size = 10;

  public String getEq() {
    return eq;
  }

  public String getEt() {
    return et;
  }

  public String getF() {
    return f;
  }

  public String getOrder() {
    return order;
  }

  public Integer getPage() {
    return page;
  }

  public String getQ() {
    return q;
  }

  public Integer getSize() {
    return size;
  }

  public String getSn() {
    return sn;
  }

  public String getSt() {
    return st;
  }

  public String getState() {
    return state;
  }

  public Date parseDateEt() throws ParseException {
    Date eDate = DateUtils.parseDate(et, f, FORMAT_DATE, FORMAT_DATE_2, FORMAT_DATE_TIME,
        FORMAT_DATE_TIME_2);
    if (StringUtils.equals(st, et)) {
      return DateUtils.addDays(eDate, 1);
    }
    return eDate;
  }

  public Date parseDateSt() throws ParseException {
    return DateUtils.parseDate(st, f, FORMAT_DATE, FORMAT_DATE_2, FORMAT_DATE_TIME,
        FORMAT_DATE_TIME_2);
  }

  public void setQ(String q) {
    this.q = q;
  }

  public void setEq(String eq) {
    this.eq = eq;
  }

  public void setState(String state) {
    this.state = state;
  }

  public void setSt(String st) {
    this.st = st;
  }

  public void setEt(String et) {
    this.et = et;
  }

  public void setF(String f) {
    this.f = f;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

}
