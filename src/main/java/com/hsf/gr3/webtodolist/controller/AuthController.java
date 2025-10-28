package com.hsf.gr3.webtodolist.controller;

import com.hsf.gr3.webtodolist.entity.User;
import com.hsf.gr3.webtodolist.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/register";
        }

        User registered = userService.register(user);

        if(registered == null) {
            model.addAttribute("message", "Email already exists.");
            return "/register";
        }

        model.addAttribute("message", "Đăng kí thành công! Hãy đăng nhập.");
        return "/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user")  User user, BindingResult result, Model model, HttpSession httpSession) {
        if(result.hasErrors()) {
            return "/login";
        }

        User currentUser = userService.login(user.getEmail(),  user.getPassword());

        if(currentUser == null) {
            model.addAttribute("message", "Sai email hoặc mật khẩu.")
            return "/login";
        }

        httpSession.setAttribute("currentUser", currentUser);

        return "redirect:/index";
    }

    @PostMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/login";
    }
}
