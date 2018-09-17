package com.mzd.mywy.myshiro.authority;

public enum AuthorityEnum {
    role("角色", "role"),
    permit("权限点", "permit");
    private String permit_dis;
    private String permit_value;

    AuthorityEnum(String permit_dis, String permit_value) {
        this.permit_dis = permit_dis;
        this.permit_value = permit_value;
    }

    public static String getvalue(AuthorityEnum permitEnum) {
        return permitEnum.permit_value;
    }
}
