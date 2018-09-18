package com.mzd.mywy.controller;


import com.mzd.mywy.exception.CongestionException;
import com.mzd.mywy.myshiro.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * 错误拦截
 */
@RestControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler(value = Permission_denied.class)
    public HashMap Except(Permission_denied throwable) {
        log.error(throwable.getMessage());
        HashMap hashMap = new HashMap();
        hashMap.put("errcode", 403);
        hashMap.put("errMsg", throwable.getMessage());
        return hashMap;
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public HashMap Except(AuthenticationException throwable) {
        log.error(throwable.getMessage());
        HashMap hashMap = new HashMap();
        hashMap.put("errcode", 403);
        hashMap.put("errMsg", throwable.getMessage());
        return hashMap;
    }

    @ExceptionHandler(value = IncorrectCredentialsException.class)
    public HashMap Except(IncorrectCredentialsException throwable) {
        log.error(throwable.getMessage());
        HashMap hashMap = new HashMap();
        hashMap.put("errcode", 403);
        hashMap.put("errMsg", throwable.getMessage());
        return hashMap;
    }


    @ExceptionHandler(value = MyMatcherException.class)
    public HashMap Except(MyMatcherException throwable) {
        log.error(throwable.getMessage());
        HashMap hashMap = new HashMap();
        hashMap.put("errcode", 403);
        hashMap.put("errMsg", throwable.getMessage());
        return hashMap;
    }


    @ExceptionHandler(value = UnknownAccountException.class)
    public HashMap Except(UnknownAccountException throwable) {
        log.error(throwable.getMessage());
        HashMap hashMap = new HashMap();
        hashMap.put("errcode", 403);
        hashMap.put("errMsg", throwable.getMessage());
        return hashMap;
    }


    @ExceptionHandler(value = LoginAndAuthorityException.class)
    public HashMap Except(LoginAndAuthorityException throwable) {
        log.error(throwable.getMessage());
        HashMap hashMap = new HashMap();
        hashMap.put("errcode", 403);
        hashMap.put("errMsg", throwable.getMessage());
        return hashMap;
    }

    @ExceptionHandler(value = CacheException.class)
    public HashMap Except(CacheException throwable) {
        log.error(throwable.getMessage());
        HashMap hashMap = new HashMap();
        hashMap.put("errcode", 403);
        hashMap.put("errMsg", throwable.getMessage());
        return hashMap;
    }

    @ExceptionHandler(value = CongestionException.class)
    public HashMap Except(CongestionException throwable) {
        log.error(throwable.getMessage());
        HashMap hashMap = new HashMap();
        hashMap.put("errcode", 403);
        hashMap.put("errMsg", throwable.getMessage());
        return hashMap;
    }


//    @ExceptionHandler(value = Exception.class)
//    public HashMap Except(Exception throwable) {
//        log.error(throwable.getMessage());
//        HashMap hashMap = new HashMap();
//        hashMap.put("errcode", 500);
//        hashMap.put("errMsg", "服务器内部错误");
//        return hashMap;
//    }
}
