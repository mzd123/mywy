package com.mzd.mywy.myshiro.login;

import com.mzd.mywy.myshiro.authority.CacheEnum;
import com.mzd.mywy.myshiro.login.bean.MyAuthenticationInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * 登入授权
 */
public class Login {
    private LoginAndAuthority loginAndAuthority;

    public Login(LoginAndAuthority loginAndAuthority) {
        this.loginAndAuthority = loginAndAuthority;
    }

    public Login() {
    }

    public boolean login(String account, String password) throws Exception {
        MyAuthenticationInfo myAuthenticationInfo = loginAndAuthority.getAuthenticationInfo(account, password);
        boolean isF = loginAndAuthority.assertCredentialsMatch(myAuthenticationInfo, password);
        if (isF) {
            //登入了
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            attributes.getRequest().getSession().setAttribute(CacheEnum.getvalue(CacheEnum.iflogin), "1");
        }
        return isF;
    }

}
