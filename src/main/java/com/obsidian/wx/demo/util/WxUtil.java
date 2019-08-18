package com.obsidian.wx.demo.util;

import com.obsidian.wx.demo.config.WxConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import weixin.popular.api.TokenAPI;

/**
 * @author huoyun
 * @date 2019/8/17-22:46
 */
@Component
public class WxUtil {
    @Autowired
    private static WxConfig wxConfig;

    public static String getAccessToken(){
        return TokenAPI.token(wxConfig.getAppID(),wxConfig.getAppsecret()).getAccess_token();
    }
}
