package com.bhhan.oauth2_server.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by hbh5274@gmail.com on 2020-08-18
 * Github : http://github.com/bhhan5274
 */

@Controller
public class WelcomeController {

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }
}
