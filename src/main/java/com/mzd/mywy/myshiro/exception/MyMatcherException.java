package com.mzd.mywy.myshiro.exception;

public class MyMatcherException extends Exception {
    public MyMatcherException() {
        super("MyMatcher can not be empty, but it is empty now!");
    }
}
