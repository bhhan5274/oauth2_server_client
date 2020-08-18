package com.bhhan.oauth2_server.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Created by hbh5274@gmail.com on 2020-08-18
 * Github : http://github.com/bhhan5274
 */

@Controller
@SessionAttributes(types = AuthorizationRequest.class)
@RequiredArgsConstructor
public class AccessConfirmationController {
    private final ClientDetailsService clientDetailsService;

    @RequestMapping("/oauth/confirm_access")
    public String getAccessConfirmation(@ModelAttribute AuthorizationRequest clientAuth, Model model){
        final ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());

        model.addAttribute("auth_request", clientAuth);
        model.addAttribute("client", client);

        return "access_confirmation";
    }
}
