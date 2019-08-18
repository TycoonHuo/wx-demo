package com.obsidian.wx.demo.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import weixin.popular.api.TokenAPI;

/**
 * @author huoyun
 * @date 2019/8/15-21:56
 */
@Component
@Data
public class WxConfig {
    @Value("${wx.appID}")
    private String appID;
    @Value("${wx.appsecret}")
    private String appsecret;
    @Value("${wx.token}")
    private String token;
    @Value("${wx.host}")
    private String host;
}
