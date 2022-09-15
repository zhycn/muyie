package com.muyie.exception;

/**
 * 调用 API 出现错误时，可以根据错误码信息来定位问题并尝试解决。
 * <p>
 * MuYie 框架提供了一个标准的错误码接口，所有自定义错误码均需实现该接口，以便统一异常处理。
 *
 * @author larry.qi
 * @since 1.2.10
 */
public interface ErrorCode {

  /**
   * 接口返回码字段，请求成功时返回[00000]，请求失败时返回错误码。
   *
   * @return 错误代码
   */
  String getCode();

  /**
   * 接口返回信息字段，请求成功返回[成功]，请求失败返回错误原因。
   *
   * @return 错误信息
   */
  String getMessage();

}
