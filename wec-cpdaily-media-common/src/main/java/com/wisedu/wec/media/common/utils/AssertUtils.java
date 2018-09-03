package com.wisedu.wec.media.common.utils;

import com.wisedu.wec.media.common.exception.ValidateException;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 用来检查参数
 * 减少if语句的使用次数
 * Created by huhaichao on 2017/6/20.
 */
public class AssertUtils {

    public static void notNull(Object value, String msg) {
        if (value == null) {
            throw new ValidateException(msg);
        }
    }

    public static void notBlank(String value, String msg) {
        if (StringUtils.isBlank(value)) {
            throw new ValidateException(msg);
        }
    }

    public static void notEmpty(List list, String msg) {
        if (list == null || list.isEmpty()){
            throw new ValidateException(msg);
        }
    }


    public static void requireTrue(boolean value, String msg) {
        if (!value) {
            throw new ValidateException(msg);
        }
    }

    public static void requireFalse(boolean value, String msg) {
        if (value) {
            throw new ValidateException(msg);
        }
    }

    public static void requireEquals(Object value1, Object value2, String msg) {
        if (!Objects.equals(value1, value2)) {
            throw new ValidateException(msg);
        }
    }
}
