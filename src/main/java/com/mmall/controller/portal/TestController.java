package com.mmall.controller.portal;


import com.mmall.common.TokenCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/test/")
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "get_cache.do")
    @ResponseBody
    public String getCache(String key){
        return TokenCache.getKey(key);
    }

    @RequestMapping(value = "test.do")
    @ResponseBody
    public String test(String str){
        logger.info("testinfo");
        logger.warn("testwarn");
        logger.error("testerror");
        return "testValue:" + str;
    }

}
