package com.mzd.mywy.myshiro.login.encryption;

public enum Encryption_Type_Enum {
    Unencrypted("明文不加密", "Unencrypted"),
    Md5("Md5加密", "Md5"),;

    private String dis;
    private String type;

    Encryption_Type_Enum(String dis, String type) {
        this.dis = dis;
        this.type = type;
    }

    public static String gettype(Encryption_Type_Enum encryption_type_enum) {
        return encryption_type_enum.type;
    }
}
