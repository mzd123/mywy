package com.mzd.mywy.myshiro.exception;

public class Permission_denied extends Exception {
    public Permission_denied() {
        //权限不足
        super("Permission denied!");
    }
}
