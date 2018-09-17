package com.mzd.mywy.myshiro.login;

import com.mzd.mywy.myshiro.authority.AuthorityEnum;
import com.mzd.mywy.myshiro.exception.MyMatcherException;
import com.mzd.mywy.myshiro.login.bean.MyAuthenticationInfo;
import com.mzd.mywy.myshiro.login.bean.My_Usersession;
import com.mzd.mywy.utils.MyStringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LoginAndAuthority {

    private MyMatcher myMatcher;
    private MyCache myCach;

    public LoginAndAuthority(MyMatcher myMatcher) {
        this.myMatcher = myMatcher;
    }

    public LoginAndAuthority(MyMatcher myMatcher, MyCache myCach) {
        this.myMatcher = myMatcher;
        this.myCach = myCach;
    }

    public LoginAndAuthority() {
    }

    public MyAuthenticationInfo getAuthenticationInfo(String account, String password) {
        return new MyAuthenticationInfo();
    }

    /**
     * 判断是否登入成功
     *
     * @param info
     * @param password
     * @return
     * @throws Exception
     */
    public boolean assertCredentialsMatch(MyAuthenticationInfo info, String password) throws Exception {
        if (myMatcher == null) {
            throw new MyMatcherException();
        }
        //数据库中的密码
        String db_password = info.getDb_password();
        //盐值
        String salt = info.getSalt();
        String type = myMatcher.getEncryption_method();
        int time = myMatcher.getTimes();
        /**
         * 反射调用自己的方法
         */
        DoMatch doMatch = new DoMatch();
        Class clazz = doMatch.getClass();
        Object o = clazz.newInstance();
        Method m = clazz.getDeclaredMethod(type, String.class, String.class, int.class);
        String getpassword = MyStringUtils.Object2String(m.invoke(o, password, salt, time));
        if (db_password.equals(getpassword)) {
            //密码相等，则将该用户放入session中
            Object o1 = info.getUser();
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            //将对象放入session中
            attributes.getRequest().getSession().setAttribute(My_Usersession.getValue(My_Usersession.cunrrentuser), o1);
            return true;
        }
        return false;
    }

    /**
     * 授权
     */
    public void doauthc() {
        //权限点--从数据库中取出来
        List<String> permit = new ArrayList<>();
        //角色--从数据库中取出来
        List<String> role = new ArrayList<>();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //将对象放入session中
        attributes.getRequest().getSession().setAttribute(AuthorityEnum.getvalue(AuthorityEnum.permit), permit);
        attributes.getRequest().getSession().setAttribute(AuthorityEnum.getvalue(AuthorityEnum.role), role);
    }

}
