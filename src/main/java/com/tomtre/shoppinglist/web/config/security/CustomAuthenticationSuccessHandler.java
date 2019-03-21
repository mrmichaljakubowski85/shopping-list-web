package com.tomtre.shoppinglist.web.config.security;

import com.tomtre.shoppinglist.web.dto.CustomSecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public static final String PRINCIPAL_SESSION_ATTRIBUTE_NAME = "userFullName";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        CustomSecurityUser customSecurityUser = (CustomSecurityUser) authentication.getPrincipal();
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            httpSession.setAttribute(PRINCIPAL_SESSION_ATTRIBUTE_NAME, customSecurityUser.getFullName());
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}