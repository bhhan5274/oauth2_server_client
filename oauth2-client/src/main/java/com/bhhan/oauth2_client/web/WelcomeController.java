package com.bhhan.oauth2_client.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by hbh5274@gmail.com on 2020-08-19
 * Github : http://github.com/bhhan5274
 */

@Controller
public class WelcomeController {
    @GetMapping("/user")
    public String user(){
        return "user";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/update")
    public String update(){
        return "update";
    }

    @GetMapping("/delete")
    public String delete(){
        return "delete";
    }
}
