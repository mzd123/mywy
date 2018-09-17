package com.mzd.mywy.plug;

import com.alibaba.fastjson.JSON;
import com.mzd.mywy.myshiro.authority.AuthorityEnum;
import com.mzd.mywy.myshiro.login.LoginAndAuthority;
import com.mzd.mywy.myshiro.login.MyCache;
import com.mzd.mywy.myshiro.login.MyMatcher;
import com.mzd.mywy.myshiro.login.bean.MyAuthenticationInfo;
import com.mzd.mywy.ormbean.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

public class MyRealm extends LoginAndAuthority {
    public MyRealm(MyMatcher myMatcher, MyCache myCache) {
        super(myMatcher, myCache);
    }

    /**
     * 登入
     *
     * @param account
     * @param password
     * @return
     */
    @Override
    public MyAuthenticationInfo getAuthenticationInfo(String account, String password) {
        /**
         * 根据账号去数据库查询这个人的信息
         */
        User user = new User("小明", "test", "dabc0b6a6a65844b922830a9eb21f937");
        String db_password = user.getPassword();
        MyAuthenticationInfo info = new MyAuthenticationInfo(user, db_password, account);
        return info;
    }

    /**
     * 授权
     */
    @Override
    public void doauthc() {
        //权限点--从数据库中取出来
        List<String> permit = new ArrayList<>();
        permit.add("a");
        //角色--从数据库中取出来
        List<String> role = new ArrayList<>();
        role.add("A");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //将对象放入session中
        attributes.getRequest().getSession().setAttribute(AuthorityEnum.getvalue(AuthorityEnum.permit), JSON.toJSONString(permit));
        attributes.getRequest().getSession().setAttribute(AuthorityEnum.getvalue(AuthorityEnum.role), JSON.toJSONString(role));

    }
}
