package com.obsidian.wx.demo.filter;

import com.obsidian.wx.demo.config.WxConfig;
import com.obsidian.wx.demo.util.WxUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import weixin.popular.api.SnsAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.token.Token;
import weixin.popular.bean.user.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author huoyun
 * @date 2019/8/17-22:25
 */
@Slf4j
@WebFilter(urlPatterns = "/home")
public class WxAuthFilter implements Filter {
    @Autowired
    WxConfig wxConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("============WxFilter============");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        User user = (User) req.getSession().getAttribute("user");

        // 用户本来要访问的URI
        String requestURI = req.getRequestURI();

        if (null == user) {
            // 跳转微信登授权登录页面 获取用户的openID
            log.warn("用户还没有登录，请登录");
            String url = SnsAPI.connectOauth2Authorize(wxConfig.getAppID(), "http://" + wxConfig.getHost() + "/auth?uri=" + requestURI, true, null);
            log.info("授权url:" + url);
            resp.sendRedirect(url);
            return;
        } else {
            log.info("user:" + user);
            chain.doFilter(request, response);
        }
    }
}
