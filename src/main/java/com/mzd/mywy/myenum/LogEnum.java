package com.mzd.mywy.myenum;

public enum LogEnum {
    faile("失败", "1"),

    success("成功", "0");

    private String value;
    private String code;

    LogEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getcode(LogEnum logEnum) {
        return logEnum.code;
    }

}
