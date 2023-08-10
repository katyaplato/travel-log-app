package com.example.backend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {
    @Controller
    public class HomeController {

        @RequestMapping(value = "/")
        public String index() {
            return "index";
        }

    }
}
