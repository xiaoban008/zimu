package com.xiaoban.zimu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiaoban
 * @create 2021/03/02 - 17:08
 */
@Controller
public class IndexCTO {
    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
