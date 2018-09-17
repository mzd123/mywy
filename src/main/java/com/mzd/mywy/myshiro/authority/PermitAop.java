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
public class PermitAop {
    @Autowired
    private MyCache myCache;
    @Autowired
    private LoginAndAuthority loginAndAuthority;

    /**
     * 判断是否有权限点访问
     *
     * @param point
     * @param permit
     * @return
     */
    @Around("@annotation(permit)")
    public Object ifpermit(ProceedingJoinPoint point, Permit permit) throws Throwable {
        String request_spot = permit.value();
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
                    String permits = MyStringUtils.Object2String(request.getSession().getAttribute(AuthorityEnum.getvalue(AuthorityEnum.permit)));
                    if (permits.equals("")) {
                        //权限不足
                        throw new Permission_denied();
                    } else {
                        jsonArray = JSON.parseArray(permits);
                    }
                } else {
                    //不缓存授权---直接读取数据库
                    loginAndAuthority.doauthc();
                    String permits = MyStringUtils.Object2String(request.getSession().getAttribute(AuthorityEnum.getvalue(AuthorityEnum.permit)));
                    if (permits.equals("")) {
                        //权限不足
                        throw new Permission_denied();
                    } else {
                        jsonArray = JSON.parseArray(permits);
                    }
                }
            } else {
                //未授权---直接读取数据库
                loginAndAuthority.doauthc();
                String permits = MyStringUtils.Object2String(request.getSession().getAttribute(AuthorityEnum.getvalue(AuthorityEnum.permit)));
                if (permits.equals("")) {
                    //权限不足
                    throw new Permission_denied();
                } else {
                    jsonArray = JSON.parseArray(permits);
                }
                request.getSession().setAttribute(CacheEnum.getvalue(CacheEnum.ifatuthc), "1");
            }
        }
        boolean ifpermit = false;
        if (jsonArray != null && jsonArray.size() > 0) {
            for (Object o : jsonArray) {
                String spot = MyStringUtils.Object2String(o);
                if (spot.equals(request_spot)) {
                    ifpermit = true;
                    break;
                }
            }
        }
        if (ifpermit) {
            //有权限---执行controller
            return point.proceed();
        } else {
            //没有权限
            throw new Permission_denied();
        }
    }

}
