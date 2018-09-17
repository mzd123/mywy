package com.mzd.mywy.myshiro.login;

public class MyCache {
    //是否缓存登入认证
    private final boolean ifcache_login;
    //是否缓存权限
    private final boolean ifcache_authc;

    public MyCache(boolean ifcache_login, boolean ifcache_authc) {
        this.ifcache_login = ifcache_login;
        this.ifcache_authc = ifcache_authc;
    }

    public boolean isIfcache_login() {
        return ifcache_login;
    }

    public boolean isIfcache_authc() {
        return ifcache_authc;
    }

}
