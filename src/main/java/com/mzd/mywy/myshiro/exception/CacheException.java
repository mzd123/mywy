package com.mzd.mywy.myshiro.exception;

public class CacheException extends Exception {
    public CacheException() {
        super("Cache is empty,You have to configure cache!");
    }
}
