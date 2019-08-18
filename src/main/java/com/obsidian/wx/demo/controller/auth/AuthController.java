package com.obsidian.wx.demo.controller.auth;

import com.obsidian.wx.demo.config.WxConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huoyun
 * @date 2019/8/17-23:14
 */
@Controller
@Slf4j
public class AuthController {
    @Autowired
    WxConfig wxConfig;

    @RequestMapping("/auth")
    public String auth(HttpServletRequest request) {
        String code = request.getParameter("code");
        log.info("code:" + code);

        SnsToken snsToken = SnsAPI.oauth2AccessToken(wxConfig.getAppID(), wxConfig.getAppsecret(), code);
        String accessToken = snsToken.getAccess_token();
        User user = SnsAPI.userinfo(accessToken, snsToken.getOpenid(), "zh_TW");
        request.getSession().setAttribute("user", user);
        log.info("uri:" + request.getParameter("uri"));
        return "redirect:" + request.getParameter("uri");
    }
}
