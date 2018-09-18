package com.mzd.mywy.myshiro.login;

import com.alibaba.fastjson.JSON;
import com.mzd.mywy.myshiro.authority.CacheEnum;
import com.mzd.mywy.myshiro.exception.Permission_denied;
import com.mzd.mywy.utils.MyStringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyshiroFilter implements Filter {
    /**
     * 第一个String 是表示请求的url
     * 第二个String 是表示是否要登入之后才能访问
     */
    //最前面的优先匹配
    private LinkedHashMap<String, String> filter_url = new LinkedHashMap();
    private List<String> list = new ArrayList<>();

    public MyshiroFilter(LinkedHashMap filter_url) {
        this.filter_url = filter_url;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        for (Map.Entry entry : filter_url.entrySet()) {
            list.add(MyStringUtils.Object2String(entry.getKey()));
        }
        //list.stream().forEach(System.out::println);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //这里需要获取的是uri，不是url
        String uri = request.getRequestURI() + "";
        String qx = getvalue(uri);
        String qx_uri = filter_url.get(qx);
        if (qx_uri == null || qx_uri.equals("") || qx_uri.equals("authc")) {
            //说明不能匿名访问
            String iflogin = MyStringUtils.Object2String(request.getSession().getAttribute(CacheEnum.getvalue(CacheEnum.iflogin)));
            if (iflogin.equals("")) {
                //说明没有登入过了
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                HashMap hashMap = new HashMap();
                hashMap.put("errcode", 403);
                hashMap.put("errMsg", "Permission denied!");
                response.getWriter().write(JSON.toJSONString(hashMap));
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 用正则匹配uri
     *
     * @param uri
     * @return
     */
    private String getvalue(String uri) {
        String value = "";
        /**
         * 避免//////test.do这种东西匹配不上
         */
        int j = 0;
        for (int i = 0; i < uri.length(); i++) {
            char c = uri.charAt(i);
            if (c != '/') {
                j = i;
                break;
            }
        }
        uri = uri.substring(j - 1);
        for (String s : list) {
            String p = s.replaceAll("\\*\\*", "*").replaceAll("\\*", ".*?");
            Pattern pattern = Pattern.compile(p);
            Matcher matcher = pattern.matcher(uri);
            if (matcher.matches()) {
                value = s;
                break;
            }
        }
        return value;
    }

    @Override
    public void destroy() {

    }
}
