package com.kkamjidot.api.mono.controller;

import com.kkamjidot.api.mono.commons.interceptor.NoAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SwaggerController {
    @GetMapping
    public String getSwagger() {
        return "swagger-ui.html";
    }

    @GetMapping("version")
    @ResponseBody
    public String getApiVersion(@Value("${springdoc.version}") String appVersion) {
        return appVersion;
    }
}
