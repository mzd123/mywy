package com.mzd.mywy.myshiro.exception;

public class LoginAndAuthorityException extends Exception {
    public LoginAndAuthorityException() {
        super("LoginAndAuthority is empty,You have to configure LoginAndAuthority!");
    }
}
