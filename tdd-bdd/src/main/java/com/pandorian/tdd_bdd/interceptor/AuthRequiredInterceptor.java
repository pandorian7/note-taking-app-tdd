package com.pandorian.tdd_bdd.interceptor;

import com.pandorian.tdd_bdd.annotations.AuthRequired;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthRequiredInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod method) {
            boolean hasAuthRequired = method.getMethodAnnotation(AuthRequired.class) != null;
            if (hasAuthRequired) {
                Object username = request.getAttribute("username");
                if (username == null) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Authentication required");
                    return false;
                }
            }
        }
        return true;
    }
}
