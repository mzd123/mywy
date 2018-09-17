package com.mzd.mywy.myshiro.exception;

public class AuthenticationException extends Exception {
    public AuthenticationException() {
        super("Failure to log in!");
    }
}
