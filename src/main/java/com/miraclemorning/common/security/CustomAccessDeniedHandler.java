package com.miraclemorning.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miraclemorning.common.exception.ApiErrorInfo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse res, AccessDeniedException accessDeniedException) throws IOException {

        // Api Error 추가
        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();
        apiErrorInfo.setMessage("Access denied");

        ObjectMapper mapper = new ObjectMapper();

        String jsonString = mapper.writeValueAsString(apiErrorInfo);

        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(403);
        res.getWriter().write(jsonString);
    }
}
