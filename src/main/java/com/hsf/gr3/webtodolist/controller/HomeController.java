package com.hsf.gr3.webtodolist.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hsf.gr3.webtodolist.entity.User;
import com.hsf.gr3.webtodolist.service.TaskService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String index(HttpSession httpSession,  Model model) {
        User user = (User)httpSession.getAttribute("currentUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "index";
    }

    // Module 6: Report endpoint
    @GetMapping("/report")
    public String report(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";
        
        Map<String, Long> statistics = taskService.getTaskStatistics(user.getId());
        long completedCount = statistics.get("completedCount");
        long totalCount = statistics.get("totalCount");
        
        model.addAttribute("completedCount", completedCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("user", user);
        
        return "report";
    }
}
