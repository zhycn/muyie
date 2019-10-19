package org.muyie.framework.http;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class SignRequest extends Request {

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
    return timestamp;
  }

  public String getVersion() {
    return version;
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

  public String toSignString() {
    StringBuffer sb = new StringBuffer();

    if (StringUtils.isNotEmpty(appAuthToken)) {
      sb.append("&appAuthToken=" + appAuthToken);
    }

    if (StringUtils.isNotEmpty(appId)) {
      sb.append("&appId=" + appId);
    }

    if (StringUtils.isNotEmpty(authToken)) {
      sb.append("&authToken=" + authToken);
    }

    if (getBizContent() != null) {
      String bizContent = JSON.toJSONString(getBizContent(), SerializerFeature.MapSortField);
      sb.append("&bizContent=" + bizContent);
    }

    if (StringUtils.isNotEmpty(method)) {
      sb.append("&method=" + method);
    }

    if (StringUtils.isNotEmpty(signType)) {
      sb.append("&signType=" + signType);
    }

    if (StringUtils.isNotEmpty(timestamp)) {
      sb.append("&timestamp=" + timestamp);
    }

    if (StringUtils.isNotEmpty(getTraceId())) {
      sb.append("&traceId=" + getTraceId());
    }

    if (StringUtils.isNotEmpty(version)) {
      sb.append("&version=" + version);
    }

    return StringUtils.substring(sb.toString(), 1);
  }

}
