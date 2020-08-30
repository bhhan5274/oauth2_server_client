package com.bhhan.client.web;

import com.bhhan.client.config.auth.LoginUser;
import com.bhhan.client.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by hbh5274@gmail.com on 2020-08-28
 * Github : http://github.com/bhhan5274
 */

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("loginName", user.getName());
            model.addAttribute("email", user.getEmail());
        }

        return "index";
    }
}
