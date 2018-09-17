package com.mzd.mywy.controller;

import com.mzd.mywy.myshiro.authority.Permit;
import com.mzd.mywy.myshiro.authority.Role;
import com.mzd.mywy.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {
    @Autowired
    private TestService testService;

    @Role("B")
    @RequestMapping("/test1.do")
    public String doTest1() {
        log.info("拥有A角色的人才能进来");
        return "你拥有A角色";
    }


    @Permit("b")
    @RequestMapping("/test2.do")
    public String doTest2() {
        log.info("拥有b权限点的人才能进来");
        return "你拥有b权限点";
    }

    @RequestMapping("/login.do")
    public String login(String account, String password) {
        try {
            boolean isF = testService.login(account, password);
            if (isF) {
                return "登入成功";
            } else {
                return "登入失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "登入失败";
        }
    }
}
