package com.codeartist.component.initializr.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 主页
 *
 * @author AiJiangnan
 * @date 2023/6/1
 */
@Controller
public class HomeController {

    @GetMapping({"", "/"})
    public String home() {
        return "redirect:/index.html";
    }
}
