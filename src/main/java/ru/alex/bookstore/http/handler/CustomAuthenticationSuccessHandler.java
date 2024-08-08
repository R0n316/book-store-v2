package ru.alex.bookstore.http.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Objects;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String referer = (String) request.getSession().getAttribute("referer");
        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, Objects.requireNonNullElse(referer, "/"));
    }
}