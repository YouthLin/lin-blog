package com.youthlin.blog.web.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 创建： lin
 * 时间： 2017-04-03 15:03
 */
@Controller
public class HomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/hello")
    public String hello() {
        LOGGER.debug("Hello.");
        return "hello";
    }

    @RequestMapping(path = {"/"})
    public String home() {
        return "index";
    }
}
