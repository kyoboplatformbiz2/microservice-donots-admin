package com.kyobo.platform.donots.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final HttpSession httpSession;
    @GetMapping("/accessDenied")
    public String accessDenied(){
        log.info((String) httpSession.getId());

        return "accessDenied";
    }
}