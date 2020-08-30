package com.bhhan.oauth2_server.web.filter;

import com.bhhan.oauth2_server.config.OAuth2Properties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by hbh5274@gmail.com on 2020-08-30
 * Github : http://github.com/bhhan5274
 */

@WebFilter(urlPatterns = "/oauth/user_info")
@RequiredArgsConstructor
public class UserInfoFilter implements Filter {
    private final OAuth2Properties oAuth2Properties;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        final String bearerToken = request.getHeader("Authorization").replace("Bearer ", "");
        String email = "";

        try{
            final Claims claims = Jwts.parser()
                    .setSigningKey(oAuth2Properties.getJwtSigningKey().getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(bearerToken).getBody();
            email = (String) claims.get("user_name");
        }catch (Exception e){
            e.printStackTrace();
        }

        servletRequest.setAttribute("email", email);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
