package com.pandorian.tdd_bdd.middlewares;

import com.pandorian.tdd_bdd.service.JWTService;
import com.pandorian.tdd_bdd.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthMiddleware implements Filter {

    private final UserService userService;

    @Autowired
    public AuthMiddleware(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String authHeader = req.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = JWTService.getUserFromToken(token);
            if (username != null) {
                request.setAttribute("username", username);
            }
        }

        chain.doFilter(request, response);
    }
}