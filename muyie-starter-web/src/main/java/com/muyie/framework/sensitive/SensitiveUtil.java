package com.muyie.framework.sensitive;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 通用数据脱敏工具类
 *
 * @author larry.qi
 * @since 1.2.9
 */
public class SensitiveUtil {

  /**
   * 构造函数
   *
   * @param hideFlag 是否需要进行屏蔽的开关；<code>ture</code>表示打开。
   */
  public SensitiveUtil(final boolean hideFlag) {
    SensitiveDataUtil.setHideFlag(hideFlag);
  }

  /**
   * 构造函数，默认开启屏蔽开关
   */
  public SensitiveUtil() {
    SensitiveDataUtil.setHideFlag(true);
  }

  /**
   * 隐藏中文姓名
   *
   * <pre>
   * SensitiveUtil.chineseNameHide(null) = null
   * SensitiveUtil.chineseNameHide("") = ""
   * SensitiveUtil.chineseNameHide("李四") = *四
   * SensitiveUtil.chineseNameHide("王麻子") = *麻子
   * SensitiveUtil.chineseNameHide("西门吹雪") = **吹雪
   * SensitiveUtil.chineseNameHide("毛利小五郞") = ***五郞
   * SensitiveUtil.chineseNameHide("江户川柯南君") = ****南君
   * SensitiveUtil.chineseNameHide("爱德华弗吉尼亚") = 爱****尼亚
   * SensitiveUtil.chineseNameHide("库尔班·热合曼") = 库****合曼
   * SensitiveUtil.chineseNameHide("Jerry") = ***ry
   * </pre>
   *
   * @param name 待部分隐藏处理的中文姓名
   * @return 部分隐藏后的中文姓名
   */
  public static String chineseNameHide(String name) {
    return SensitiveDataUtil.nameHide(name);
  }

  /**
   * 敏感信息缺省隐藏方式处理：显示前1/3和后1/3，其他*号代替。内容长度不能被3整除时，显示前ceil[length/3.0]和后floor[ length/3.0]
   *
   * <pre>
   * SensitiveUtil.defaultHide(null) = null
   * SensitiveUtil.defaultHide("") = ""
   * SensitiveUtil.defaultHide("李四") = 李*
   * SensitiveUtil.defaultHide("王麻子") = 王*子
   * SensitiveUtil.defaultHide("西门吹雪") = 西门*雪
   * SensitiveUtil.defaultHide("毛利小五郞") = 毛利**郞
   * SensitiveUtil.defaultHide("江户川柯南君") = 江户**南君
   * SensitiveUtil.defaultHide("爱德华弗吉尼亚") = 爱德华**尼亚
   * SensitiveUtil.defaultHide("库尔班·热合曼") = 库尔班**合曼
   * SensitiveUtil.defaultHide("Jerry") = Je**y
   * </pre>
   *
   * @param sensitiveData 待部分隐藏处理的敏感信息
   * @return 部分隐藏后的数据
   */
  public static String defaultHide(String sensitiveData) {
    return SensitiveDataUtil.defaultHide(sensitiveData);
  }

  /**
   * 隐藏昵称：显示首/尾各1位，中间加**
   *
   * <pre>
   * SensitiveUtil.nickHide(null) = null
   * SensitiveUtil.nickHide("") = ""
   * SensitiveUtil.nickHide("李四") = 李**四
   * SensitiveUtil.nickHide("王麻子") = 王**子
   * SensitiveUtil.nickHide("西门吹雪") = 西**雪
   * SensitiveUtil.nickHide("毛利小五郞") = 毛**郞
   * SensitiveUtil.nickHide("江户川柯南君") = 江**君
   * SensitiveUtil.nickHide("爱德华弗吉尼亚") = 爱**亚
   * SensitiveUtil.nickHide("库尔班·热合曼") = 库**曼
   * SensitiveUtil.nickHide("Jerry") = J**y
   * </pre>
   *
   * @param nick 待部分隐藏处理的昵称
   * @return 部分隐藏后的昵称
   */
  public static String nickHide(String nick) {
    return SensitiveDataUtil.nickHide(nick);
  }

  /**
   * 隐藏密码：固定返回6位*
   *
   * <pre>
   * SensitiveUtil.passwordHide(null) = null
   * SensitiveUtil.passwordHide("") = ""
   * SensitiveUtil.passwordHide("12345678") = ******
   * </pre>
   *
   * @param password 待隐藏处理的密码
   * @return 隐藏后的密码
   */
  public static String passwordHide(String password) {
    if (StrUtil.isBlank(password)) {
      return password;
    }
    if (!SensitiveDataUtil.needHide()) {
      return password;
    }
    return StrUtil.repeat('*', 6);
  }

  /**
   * 【中国大陆车牌，包含普通车辆、新能源车辆】车牌中间用*代替，如果是错误的车牌，则不处理。
   *
   * <pre>
   * SensitiveUtil.carLicenseHide(null) = null
   * SensitiveUtil.carLicenseHide("") = ""
   * SensitiveUtil.carLicenseHide("苏D40000") = 苏D4***0
   * SensitiveUtil.carLicenseHide("陕A12345D") = 陕A1****D
   * SensitiveUtil.carLicenseHide("京A123") = 京A123
   * </pre>
   *
   * @param carLicense 待部分隐藏处理的车牌号
   * @return 部分隐藏后的车牌
   */
  public static String carLicenseHide(String carLicense) {
    if (StrUtil.isBlank(carLicense)) {
      return carLicense;
    }
    if (!SensitiveDataUtil.needHide()) {
      return carLicense;
    }
    return DesensitizedUtil.carLicense(carLicense);
  }

  /**
   * 对银行卡号进行部分隐藏处理的简便方法，默认不做验证，由使用者自己保障。
   *
   * <pre>
   * SensitiveUtil.bankCardHide(null) = null
   * SensitiveUtil.bankCardHide("") = ""
   * SensitiveUtil.bankCardHide("612526198319131434") = 612526********1434
   * </pre>
   *
   * @param bankCardNo 待部分隐藏处理的银行卡号
   * @return 部分隐藏后的银行卡号
   */
  public static String bankCardHide(String bankCardNo) {
    return SensitiveDataUtil.bankCardNoHide(bankCardNo);
  }

  /**
   * 对大陆身份证号进行部分隐藏处理的简便方法，默认不做验证，由使用者自己保障。
   *
   * <pre>
   * SensitiveUtil.idCardHide(null) = null
   * SensitiveUtil.idCardHide("") = ""
   * SensitiveUtil.idCardHide("362397201201012384") = "3****************4"
   * </pre>
   *
   * @param idCardNo 待部分隐藏处理的身份证号
   * @return 部分隐藏后的身份证号
   */
  public static String idCardHide(String idCardNo) {
    return SensitiveDataUtil.idCardNoHide(idCardNo);
  }

  /**
   * 对Email进行部分隐藏处理的简便方法，默认不做验证，由使用者自己保障。
   *
   * <pre>
   * SensitiveUtil.emailHide(null) = null
   * SensitiveUtil.emailHide("") = ""
   * SensitiveUtil.emailHide("yaaachaa@163.com") = "yaa***@163.com"
   * </pre>
   *
   * @param email 待部分隐藏处理的的Email
   * @return 部分隐藏后的Email
   */
  public static String emailHide(String email) {
    return SensitiveDataUtil.emailHide(email);
  }

  /**
   * 手机号码通用隐藏规则（包括港澳台地区），隐藏中间六位或四位，适用于网站以及客户端
   *
   * <pre>
   * SensitiveUtil.phoneHide(null) = null
   * SensitiveUtil.phoneHide("") = ""
   * SensitiveUtil.phoneHide("13071835358") = 130******58
   * SensitiveUtil.phoneHide("3071835358") = 307****358
   * SensitiveUtil.phoneHide("071835358") = 07****358
   * SensitiveUtil.phoneHide("835358") = 8****8
   * </pre>
   *
   * @param phoneNo 待部分隐藏处理的手机号
   * @return 部分隐藏后的手机号
   */
  public static String phoneHide(String phoneNo) {
    return SensitiveDataUtil.cellphoneHide(phoneNo);
  }

  /**
   * 对登录帐号进行部分隐藏处理：如果是手机号，显示前3和后2位；如果是email，只显示用户名的前3位+*+@域名；如果都不是， 隐藏中间四位。
   *
   * <pre>
   * SensitiveUtil.logonHide(null) = null
   * SensitiveUtil.logonHide("") = ""
   * SensitiveUtil.logonHide("15087653459") = 150******59
   * SensitiveUtil.logonHide("yaaachaa@163.com") = yaa***@163.com
   * SensitiveUtil.logonHide("071835358") = 07****358
   * SensitiveUtil.logonHide("835358") = 8****8
   * </pre>
   *
   * @param logonId 待部分隐藏处理的登录帐号，可能是手机号或email。
   * @return 部分隐藏后的登录帐号
   */
  public static String logonHide(String logonId) {
    return SensitiveDataUtil.logonIdHide(logonId);
  }


  /**
   * hideFlag setter
   *
   * @param hideFlag 是否需要进行屏蔽的开关；<code>ture</code>表示打开。
   */
  public static void setHideFlag(final boolean hideFlag) {
    SensitiveDataUtil.setHideFlag(hideFlag);
  }

  /**
   * hideFlag getter
   *
   * @return 是否需要进行隐藏屏蔽
   */
  public static boolean needHide() {
    return SensitiveDataUtil.needHide();
  }


}
