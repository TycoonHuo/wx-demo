package com.obsidian.wx.demo.controller;

import com.obsidian.wx.demo.config.WxConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weixin.popular.api.MenuAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.menu.Button;
import weixin.popular.bean.menu.MenuButtons;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.token.Token;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.bean.xmlmessage.XMLVideoMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author huoyun
 * @date 2019/8/15-21:30
 */
@RestController
@RequestMapping("/wx")
@Slf4j
public class WxController {

    @Autowired
    private WxConfig wxConfig;

    private static ExpireKey expireKey = new DefaultExpireKey();

    /**
     * 这些参数都是微信那边发来的。
     * Get请求负责接入
     *
     * @param response
     * @param request
     * @return 按照规则需要返回echostr。
     */
    @GetMapping("/test")
    public String test(HttpServletResponse response, HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        //验证请求签名
        if (!signature.equals(SignatureUtil.generateEventMessageSignature(wxConfig.getToken(), timestamp, nonce))) {
            System.out.println("The request signature is invalid");
            return "The request signature is invalid";
        } else {
            return echostr;
        }
    }

    /**
     * 处理交互逻辑
     */
    @PostMapping("/test")
    public String handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        ServletOutputStream outputStream = response.getOutputStream();

        if (inputStream != null) {
            //转换XML
            EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class, inputStream);
            String key = eventMessage.getFromUserName() + "__"
                    + eventMessage.getToUserName() + "__"
                    + eventMessage.getMsgId() + "__"
                    + eventMessage.getCreateTime();
            log.info("key: " + key);
            if (!expireKey.exists(key)) {
                expireKey.add(key);
            }

            log.info("event: " + ToStringBuilder.reflectionToString(eventMessage, ToStringStyle.MULTI_LINE_STYLE));
//
//            if ("login".equals(eventMessage.getEventKey())) {
//                XMLTextMessage xmlTextMessage = new XMLTextMessage(
//                        eventMessage.getFromUserName(),
//                        eventMessage.getToUserName(),
//                        "请登录<a href='http://sx4xnw.natappfree.cc/login'>login</a>"
//                );
//                xmlTextMessage.outputStreamWrite(outputStream);
//            }
        }
        return "";
    }
}
