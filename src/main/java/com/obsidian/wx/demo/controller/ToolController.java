package com.obsidian.wx.demo.controller;

import com.obsidian.wx.demo.config.WxConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weixin.popular.api.MenuAPI;
import weixin.popular.api.MessageAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.menu.Button;
import weixin.popular.bean.menu.MenuButtons;
import weixin.popular.bean.message.templatemessage.TemplateMessage;
import weixin.popular.bean.message.templatemessage.TemplateMessageItem;
import weixin.popular.bean.token.Token;

import java.util.LinkedHashMap;

/**
 * @author huoyun
 * @date 2019/8/17-19:15
 */
@RestController
@RequestMapping("tools")
@Slf4j
public class ToolController {
    @Autowired
    private WxConfig wxConfig;

    //  spring以及对象初始化怎么一回事  private Token token = TokenAPI.token(wxConfig.getAppID(), wxConfig.getAppsecret());
    @RequestMapping("createMenu")
    public BaseResult createMenu() {
        Token token = TokenAPI.token(wxConfig.getAppID(), wxConfig.getAppsecret());
        Button sub1 = new Button();
        sub1.setType("view");
        sub1.setName("View Book");
        sub1.setUrl("http://www.baidu.com/");

        Button sub2 = new Button();
        sub2.setType("view");
        sub2.setName("日入万元");
        sub2.setUrl("http:" + wxConfig.getHost() + "/home");


        Button sub3 = new Button();
        sub3.setType("pic_weixin");
        sub3.setName("PIC");
        sub3.setKey("click-02");

        MenuButtons btn1 = new MenuButtons();
        btn1.setButton(new Button[]{sub1, sub2, sub3});

        String menuStr = "{\n" +
                "    \"button\": [\n" +
                "        {\n" +
                "            \"name\": \"扫码\", \n" +
                "            \"sub_button\": [\n" +
                "                {\n" +
                "                    \"type\": \"scancode_waitmsg\", \n" +
                "                    \"name\": \"扫码带提示\", \n" +
                "                    \"key\": \"rselfmenu_0_0\", \n" +
                "                    \"sub_button\": [ ]\n" +
                "                }, \n" +
                "                {\n" +
                "                    \"type\": \"scancode_push\", \n" +
                "                    \"name\": \"扫码推事件\", \n" +
                "                    \"key\": \"rselfmenu_0_1\", \n" +
                "                    \"sub_button\": [ ]\n" +
                "                }\n" +
                "            ]\n" +
                "        }, \n" +
                "        {\n" +
                "            \"name\": \"发图\", \n" +
                "            \"sub_button\": [\n" +
                "                {\n" +
                "                    \"type\": \"pic_sysphoto\", \n" +
                "                    \"name\": \"系统拍照发图\", \n" +
                "                    \"key\": \"rselfmenu_1_0\", \n" +
                "                   \"sub_button\": [ ]\n" +
                "                 }, \n" +
                "                {\n" +
                "                    \"type\": \"pic_photo_or_album\", \n" +
                "                    \"name\": \"拍照或者相册发图\", \n" +
                "                    \"key\": \"rselfmenu_1_1\", \n" +
                "                    \"sub_button\": [ ]\n" +
                "                }, \n" +
                "                {\n" +
                "                    \"type\": \"pic_weixin\", \n" +
                "                    \"name\": \"微信相册发图\", \n" +
                "                    \"key\": \"rselfmenu_1_2\", \n" +
                "                    \"sub_button\": [ ]\n" +
                "                }\n" +
                "            ]\n" +
                "        } \n" +
                "    ]\n" +
                "}";
        BaseResult baseResult = MenuAPI.menuCreate(token.getAccess_token(), btn1);
        return baseResult;
    }

    @RequestMapping("template")
    public Object templateMsg() {
        Token token = TokenAPI.token(wxConfig.getAppID(), wxConfig.getAppsecret());

        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setTouser("oTk2Gs3s1AVVKAl6KZLhQlND4f3I");
        templateMessage.setTemplate_id("z0maADnGuo1yOVhlzSIeJ8LcrGnyB6Tyj6ZR16fNbGw");

        LinkedHashMap<String, TemplateMessageItem> templateData = new LinkedHashMap<>();
        templateData.put("user", new TemplateMessageItem("黄先生", "yellow"));
        templateMessage.setData(templateData);

        log.info(token.getAccess_token());
        return MessageAPI.messageTemplateSend(token.getAccess_token(), templateMessage);
    }
}
