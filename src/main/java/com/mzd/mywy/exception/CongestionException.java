package com.mzd.mywy.exception;

public class CongestionException extends Exception {
    public CongestionException() {
        super("当前业务繁忙，请稍后再试");
    }
}
