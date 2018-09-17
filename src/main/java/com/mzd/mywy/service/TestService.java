package com.mzd.mywy.service;

import com.mzd.mywy.myshiro.login.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    private Login loginAndAuthority;

    public boolean login(String account, String password) throws Exception {
        return loginAndAuthority.login(account, password);
    }

}
