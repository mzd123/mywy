package com.mzd.mywy.myshiro.exception;

public class UnknownAccountException extends Exception {
    public UnknownAccountException() {
        //用户不存在
        super("The user does not exist!");
    }
}
