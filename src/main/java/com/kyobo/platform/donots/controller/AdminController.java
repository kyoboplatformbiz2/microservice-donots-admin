package com.kyobo.platform.donots.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "accessDenied";
    }
}