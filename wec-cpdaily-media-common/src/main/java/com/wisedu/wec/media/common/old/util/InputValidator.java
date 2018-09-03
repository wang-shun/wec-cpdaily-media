package com.wisedu.wec.media.common.old.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.wisedu.wec.media.common.old.po.CampusMedia;



public class InputValidator {
  public static final String INVALID_CLUSTER_NAMESPACE_MESSAGE = "只允许输入数字，字母和符号 - _ .";
  public static final String INVALID_NAMESPACE_NAMESPACE_MESSAGE =
      "不允许以.json, .yml, .yaml, .xml, .properties结尾";
  public static final String CLUSTER_NAMESPACE_VALIDATOR = "[0-9a-zA-Z_.-]+";
  public static final String APP_NAMESPACE_VALIDATOR =
      "[a-zA-Z0-9._-]+(?<!\\.(json|yml|yaml|xml|properties))$";
  private static final Pattern CLUSTER_NAMESPACE_PATTERN =
      Pattern.compile(CLUSTER_NAMESPACE_VALIDATOR);
  private static final Pattern APP_NAMESPACE_PATTERN = Pattern.compile(APP_NAMESPACE_VALIDATOR);

  private static final Pattern MEDIA_ID_PATTERN = Pattern.compile("[0-9a-z_]+");


  public static boolean isValidClusterNamespace(String input) {
    Matcher matcher = CLUSTER_NAMESPACE_PATTERN.matcher(input);
    return matcher.matches();
  }

  public static boolean isValidMediaApply(CampusMedia campusMedia) {

    // 必填项 校园号ID程序生成，不再由用户填写
//    String wid = campusMedia.getWid();
    String name = campusMedia.getName();
    String icon = campusMedia.getIcon();
    String description = campusMedia.getDescription();
//    String password = campusMedia.getLoginPassword();
    if (StringUtils.isAnyEmpty(name, icon, description)) {
      return false;
    }
//
//    // 校园号ID 6~12位
//    int widLength = wid.length();
//    if (widLength < 6 || widLength > 12) {
//      return false;
//    }

    // 校园号介绍 10~30个字
    int length = description.length();
    if (length < 1 || length > 100) {
      return false;
    }

    // 校园号ID 6~12位数字或英文字母组合，区分大小写，只包含:"_"特殊符号
//    Matcher matcher = MEDIA_ID_PATTERN.matcher(wid);
//    return matcher.matches();
    return true;
  }

}
