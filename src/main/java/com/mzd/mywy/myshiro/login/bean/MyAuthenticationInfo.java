package com.mzd.mywy.myshiro.login.bean;

public class MyAuthenticationInfo {
    private Object user;
    private String db_password;
    private String salt;

    public MyAuthenticationInfo(Object user, String db_password, String salt) {
        this.user = user;
        this.db_password = db_password;
        this.salt = salt;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public MyAuthenticationInfo() {
    }
}
