package com.kyobo.platform.donots.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/login")
@RequiredArgsConstructor
@RestController
public class LoginController {
    @PostMapping("/signUp")
    public String loginForm(){
        return "loginForm";
    }
}