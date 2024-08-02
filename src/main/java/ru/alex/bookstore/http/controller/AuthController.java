package ru.alex.bookstore.http.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alex.bookstore.dto.UserCreateEditDto;
import ru.alex.bookstore.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request){
        request.getSession().setAttribute("referer",request.getHeader("Referer"));
        return "adaptive/auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(){
        return "adaptive/auth/registration";
    }

    @PostMapping("/registration")
    public String register(UserCreateEditDto user){
        userService.register(user);
        return "redirect:/auth/login";
    }
}
