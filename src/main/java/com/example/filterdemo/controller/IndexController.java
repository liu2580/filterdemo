package com.example.filterdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liutianqi
 * @date 2019/11/26
 */
@Controller
@RequestMapping ("")
public class IndexController {

    @RequestMapping ("/index")
    public String get(){
        return "index.html" ;
    }

}
