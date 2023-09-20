package com.muyie.framework.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * 国密算法工具类
 *
 * @author larry.qi
 * @since 1.3.1
 */
@UtilityClass
public class SmSecureUtil {

  static {
    Security.addProvider(new BouncyCastleProvider());
  }

  /**
   * 随机生成SM4密钥
   *
   * @return SM4密钥
   */
  public static String getRandomSm4Key() {
    return RandomStringUtils.randomAlphabetic(16);
  }

  /**
   * SM3 摘要签名算法
   *
   * @param data 明文数据
   * @return 摘要
   */
  public static String sm3(String data) {
    return SmUtil.sm3().digestHex(data, CharsetUtil.CHARSET_UTF_8);
  }

  /**
   * SM3 摘要签名算法
   *
   * @param data 明文数据
   * @param salt 盐值
   * @return 摘要
   */
  public static String sm3WithSalt(String data, String salt) {
    return SmUtil.sm3WithSalt(salt.getBytes()).digestHex(data, CharsetUtil.CHARSET_UTF_8);
  }

  /**
   * SM4 对称加密
   *
   * @param data   明文数据
   * @param sm4Key SM4密钥
   * @return 密文数据
   */
  public static String sm4EncryptHex(String data, String sm4Key) {
    return SmUtil.sm4(sm4Key.getBytes()).encryptHex(data, CharsetUtil.CHARSET_UTF_8);
  }

  /**
   * SM4 对称加密
   *
   * @param data   密文数据
   * @param sm4Key SM4密钥
   * @return 明文数据
   */
  public static String sm4DecryptStr(String data, String sm4Key) {
    return SmUtil.sm4(sm4Key.getBytes()).decryptStr(data, CharsetUtil.CHARSET_UTF_8);
  }

}
