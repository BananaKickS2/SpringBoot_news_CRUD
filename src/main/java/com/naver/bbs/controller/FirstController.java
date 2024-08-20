package com.naver.bbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/greetings")
    public String greetings(Model model) {
        model.addAttribute("username", "홍길동");
        return "greetings";
    }


} // class end
