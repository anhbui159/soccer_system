package com.abui.soccer_system.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedException implements AccessDeniedHandler {
    private final ObjectMapper mapper;
    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.out.println("Access denied");
        accessDeniedException.printStackTrace();
        Map<String, Object> errorDetails = new HashMap<>();
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        errorDetails.put("message", "Access denied");
        errorDetails.put("details", accessDeniedException.getMessage());
        errorDetails.put("status", HttpStatus.FORBIDDEN.value());
        res.setStatus(403);
        mapper.writeValue(res.getWriter(), errorDetails);
    }
}
