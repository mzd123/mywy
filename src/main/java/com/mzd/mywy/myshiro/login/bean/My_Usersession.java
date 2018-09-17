package com.mzd.mywy.myshiro.login.bean;

public enum My_Usersession {
    cunrrentuser("当前用户", "user");

    private String dis;
    private String value;

    My_Usersession(String dis, String value) {
        this.dis = dis;
        this.value = value;
    }

    public static String getValue(My_Usersession my_usersession) {
        return my_usersession.value;
    }
}
