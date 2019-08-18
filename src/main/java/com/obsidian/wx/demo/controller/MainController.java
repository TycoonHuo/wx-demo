package com.obsidian.wx.demo.controller;

import com.obsidian.wx.demo.config.WxConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weixin.popular.bean.user.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huoyun
 * @date 2019/8/17-21:39
 */
@RequestMapping("home")
@RestController
@Slf4j
public class MainController {
    @Autowired
    WxConfig wxConfig;

    @GetMapping()
    public User login(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user;
    }
}
