package com.mzd.mywy.plug;

import com.mzd.mywy.myshiro.login.*;
import com.mzd.mywy.myshiro.login.encryption.Encryption_Type_Enum;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

@Component
public class MyShiroConfig {

    @Bean("myMatcher")
    public MyMatcher getMyMatcher() {
        return new MyMatcher(Encryption_Type_Enum.gettype(Encryption_Type_Enum.Md5), 2);
    }

    @Bean("loginAndAuthority")
    public LoginAndAuthority getLoginAndAuthority() {
        return new MyRealm(getMyMatcher(), getMyCache());
    }

    @Bean("mycache")
    public MyCache getMyCache() {
        //第一个表示是否缓存登入信息---即是不是要没发送一次请求都去登入一次
        //第二个表示是否缓存授权信息---即每次aop拦截的时候是否要去走一趟数据库看看有没有权限
        return new MyCache(true, true);
    }


    @Bean("login")
    public Login getLogin() {
        return new Login(getLoginAndAuthority());
    }


    @Bean("myshiroFilter")
    public MyshiroFilter getMyshiroFilter() {
        LinkedHashMap<String, String> filter_url = new LinkedHashMap();
        filter_url.put("/swagger*/**", "anon");
        filter_url.put("/webjars/**", "anon");
        filter_url.put("/v2/**", "anon");
        //============================druid====================================
        filter_url.put("/druid/**", "anon");
        //============================登入====================================
        filter_url.put("/login.do", "anon");
        //============================退出====================================
        filter_url.put("/login.do", "anon");
        filter_url.put("/**", "authc");
        MyshiroFilter myshiroFilter = new MyshiroFilter(filter_url);
        return myshiroFilter;
    }
}
