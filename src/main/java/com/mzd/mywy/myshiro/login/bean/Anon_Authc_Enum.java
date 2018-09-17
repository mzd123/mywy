package com.mzd.mywy.myshiro.login.bean;

public enum Anon_Authc_Enum {
    anon("匿名登入", "anon"),
    authc("登入之后才能访问", "authc");
    private String sign;
    private String value;

    Anon_Authc_Enum(String sign, String value) {
        this.sign = sign;
        this.value = value;
    }

    public static String getvalue(Anon_Authc_Enum anon_authc) {
        return anon_authc.value;
    }
}
