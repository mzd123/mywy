package com.mzd.mywy.controller;

import com.mzd.mywy.exception.CongestionException;
import com.mzd.mywy.service.MsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsController {
    @Autowired
    private MsService msService;

    @RequestMapping("/select_info.do")
    public String select_info(String product_id) {
        return msService.select_info(product_id);
    }

    @RequestMapping("/order.do")
    public String order(String product_id) throws CongestionException {
        return msService.order3(product_id);
    }
}
