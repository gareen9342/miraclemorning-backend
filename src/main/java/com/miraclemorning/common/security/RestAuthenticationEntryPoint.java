package com.miraclemorning.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miraclemorning.common.exception.ApiErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");

        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();
        if(InsufficientAuthenticationException.class == authException.getClass()){
            apiErrorInfo.setMessage("Not Logged in");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }else{
            apiErrorInfo.setMessage("Bad Request");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }

        String jsonString = objectMapper.writeValueAsString(apiErrorInfo);
        response.getWriter().write(jsonString);

    }


}
