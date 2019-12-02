package com.example.filterdemo.controller;


import com.example.filterdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @author liutianqi
 * @date 2019/11/26
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAllUser());
        System.out.println(userService.findAllUser());
        return "users.html";
    }
}
