package org.muyie.framework.http;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.muyie.framework.config.MuyieConstants;
import org.springframework.util.StringUtils;

/**
 * 签名请求实体
 */
public class SignRequestEntity extends RequestEntity {

  private static final long serialVersionUID = 1L;

  private String appId;

  private String method;

  private String timestamp;

  private String version;

  private String signType;

  private String sign;

  private String authToken;

  private String appAuthToken;

  public String getAppAuthToken() {
    return appAuthToken;
  }

  public String getAppId() {
    return appId;
  }

  public String getAuthToken() {
    return authToken;
  }

  public String getMethod() {
    return method;
  }

  public String getSign() {
    return sign;
  }

  public String getSignType() {
    return signType;
  }

  public String getTimestamp() {
    return StringUtils.isEmpty(timestamp)
        ? DateFormatUtils.format(new Date(), MuyieConstants.FORMAT_DATE_TIME)
        : timestamp;
  }

  public String getVersion() {
    return StringUtils.isEmpty(version) ? "1.0" : version;
  }

  public void setAppAuthToken(String appAuthToken) {
    this.appAuthToken = appAuthToken;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public void setSignType(String signType) {
    this.signType = signType;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public void setVersion(String version) {
    this.version = version;
  }

}
