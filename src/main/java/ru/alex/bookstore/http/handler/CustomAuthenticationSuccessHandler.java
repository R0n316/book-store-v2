package ru.alex.bookstore.http.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String referer = (String) request.getSession().getAttribute("referer");
        if (referer != null && !referer.startsWith("/auth")) {
            clearAuthenticationAttributes(request);
            getRedirectStrategy().sendRedirect(request, response, referer);
        } else {
            clearAuthenticationAttributes(request);
            getRedirectStrategy().sendRedirect(request, response, "/");
        }
    }
}