package com.mzd.mywy.myshiro.exception;

public class IncorrectCredentialsException extends Exception {
    public IncorrectCredentialsException() {
        //密码不对
        super("Password and account do not match!");
    }
}
