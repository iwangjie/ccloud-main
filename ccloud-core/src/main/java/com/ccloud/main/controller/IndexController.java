package com.ccloud.main.controller;

import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.service.IBusinessUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangjie
 */
@RestController
@Slf4j
public class IndexController {

    @Autowired
    private IBusinessUserService IBusinessUserService;

    @RequestMapping("/")
    public String helloWorld() {
        List<BusinessUser> list = IBusinessUserService.list();
        list.forEach(e->log.info("username:{}",e.getUsername()));
        return "hello world!";
    }
}
