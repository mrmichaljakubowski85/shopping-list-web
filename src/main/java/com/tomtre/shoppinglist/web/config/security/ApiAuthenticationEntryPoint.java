package com.tomtre.shoppinglist.web.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomtre.shoppinglist.web.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApiAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private static final Logger logger = Logger.getLogger(ApiAuthenticationEntryPoint.class.getName());

    private final ObjectMapper objectMapper;

    public ApiAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.log(Level.INFO, "REST Exception occurred:", authException);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                System.currentTimeMillis());
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }

}
