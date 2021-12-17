package com.vae.util;

/*
* 运用到 ExceptionHandle 中,进行返回错误
* */
public class ErrorUtil {
    public static String parameterErrorFormat(String field, String msg) {
        return "【" + field + " : " + msg + "】";
    }
}
