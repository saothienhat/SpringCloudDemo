package com.saothienhat.configserver.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController("/configserver")
public class ConfigServerController {

	@Value("${message:Hello default}")
    private String message;

    @RequestMapping("/configurations")
    public String getConfigData() {
        return this.message;
    }
}
