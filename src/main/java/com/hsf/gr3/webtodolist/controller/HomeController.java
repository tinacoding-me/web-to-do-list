package com.hsf.gr3.webtodolist.controller;

import com.hsf.gr3.webtodolist.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomeController {

    @GetMapping("/")
    public String index(HttpSession httpSession,  Model model) {
        User user = (User)httpSession.getAttribute("currentUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "index";
    }
}
