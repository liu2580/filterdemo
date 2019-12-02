package com.example.filterdemo.controller;

import com.example.filterdemo.dao.User;
import com.example.filterdemo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liutianqi
 * @date 2019/12/2
 */
@Controller
public class SigninController {
    @Autowired
    private UserService userService;

    @GetMapping("/signin")
    public String signInput() {
        return "signin.html";
    }

    @PostMapping("/signin")
    public String sign(String username, String password, HttpServletResponse resp) {
        System.out.println(username + "-" + password);
        User user = userService.getUserByUsername(username);
//        if (user != null && user.getPassword().equals(password)) {
//            return "redirect:/index";
//        }
//        return "signin-error.html";
        if (user != null && user.getPassword().equals(password)) {
            // 生成cookie
            Cookie cookie = CookieUtil.generateCookie(user);

            System.out.println("write new cookie:" + cookie.getValue());
            // 将cookie写入客户端
            resp.addCookie(cookie);
            return "redirect:/index";
        }
        return "signin-error";
    }
}
