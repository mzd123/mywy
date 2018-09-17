package com.mzd.mywy.myshiro.authority;

public enum CacheEnum {
    ifatuthc("ifatuthc", "是否授权"),
    iflogin("iflogin", "是否登入");

    private String value;
    private String dis;

    CacheEnum(String value, String dis) {
        this.value = value;
        this.dis = dis;
    }

    public static String getvalue(CacheEnum cacheEnum) {
        return cacheEnum.value;
    }
}
