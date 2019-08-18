package com.obsidian.wx.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com.obsidian.wx.demo.filter")
public class WxDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxDemoApplication.class, args);
    }

}
