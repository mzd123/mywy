package com.mzd.mywy.myshiro.authority;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mzd.mywy.myshiro.exception.CacheException;
import com.mzd.mywy.myshiro.exception.LoginAndAuthorityException;
import com.mzd.mywy.myshiro.exception.Permission_denied;
import com.mzd.mywy.myshiro.login.LoginAndAuthority;
import com.mzd.mywy.myshiro.login.MyCache;
import com.mzd.mywy.myshiro.login.bean.My_Usersession;
import com.mzd.mywy.service.TestService;
import com.mzd.mywy.utils.MyStringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class RoleAop {
    @Autowired
    private MyCache myCache;
    @Autowired
    private LoginAndAuthority loginAndAuthority;

    /**
     * 判断是否有角色
     *
     * @param point
     * @param role
     * @return
     */
    @Around("@annotation(role)")
    public Object ifRole(ProceedingJoinPoint point, Role role) throws Throwable {
        String request_role = role.value();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        JSONArray jsonArray = new JSONArray();
        if (loginAndAuthority == null) {
            throw new LoginAndAuthorityException();
        }
        //获取所有的权限点
        if (myCache == null) {
            //没有配置缓存
            throw new CacheException();
        } else {
            //是否缓存授权
            boolean ifcache_authc = myCache.isIfcache_authc();
            //是否授权过
            String ifatuthc = MyStringUtils.Object2String(request.getSession().getAttribute(CacheEnum.getvalue(CacheEnum.ifatuthc)));
            if (ifatuthc.equals("1")) {
                //缓存授权
                if (ifcache_authc) {
                    String roles = MyStringUtils.Object2String(request.getSession().getAttribute(AuthorityEnum.getvalue(AuthorityEnum.role)));
                    if (roles.equals("")) {
                        //权限不足
                        throw new Permission_denied();
                    } else {
                        jsonArray = JSON.parseArray(roles);
                    }
                } else {
                    //不缓存授权---直接读取数据库
                    loginAndAuthority.doauthc();
                    String roles = MyStringUtils.Object2String(request.getSession().getAttribute(AuthorityEnum.getvalue(AuthorityEnum.role)));
                    if (roles.equals("")) {
                        //权限不足
                        throw new Permission_denied();
                    } else {
                        jsonArray = JSON.parseArray(roles);
                    }
                }
            } else {
                //未授权---直接读取数据库
                loginAndAuthority.doauthc();
                String roles = MyStringUtils.Object2String(request.getSession().getAttribute(AuthorityEnum.getvalue(AuthorityEnum.role)));
                if (roles.equals("")) {
                    //权限不足
                    throw new Permission_denied();
                } else {
                    jsonArray = JSON.parseArray(roles);
                }
                request.getSession().setAttribute(CacheEnum.getvalue(CacheEnum.ifatuthc), "1");
            }
        }
        boolean ifrole = false;
        if (jsonArray != null) {
            for (Object o : jsonArray) {
                String myrole = MyStringUtils.Object2String(o);
                if (myrole.equals(request_role)) {
                    ifrole = true;
                    break;
                }
            }
        }
        if (ifrole) {
            return point.proceed();
        } else {
            throw new Permission_denied();
        }
    }
}
