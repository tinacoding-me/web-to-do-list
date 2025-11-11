package com.hsf.gr3.webtodolist.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hsf.gr3.webtodolist.entity.Task;
import com.hsf.gr3.webtodolist.entity.User;
import com.hsf.gr3.webtodolist.service.TaskService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/add/{projectId}")
    public String addTask(@PathVariable Long projectId,
                          @RequestParam String title,
                          @RequestParam String description,@RequestParam LocalDate deadline,
                          HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/";
        taskService.addTask(projectId, title, description, user.getId(), deadline);
        return "redirect:/projects/" + projectId;
    }

    @GetMapping("/toggle/{taskId}/{projectId}")
    public String toggleTask(@PathVariable Long taskId,
                             @PathVariable Long projectId) {
        taskService.toggleStatus(taskId);
        return "redirect:/projects/" + projectId;
    }

    @GetMapping("/delete/{taskId}/{projectId}")
    public String deleteTask(@PathVariable Long taskId,
                             @PathVariable Long projectId) {
        taskService.deleteTask(taskId);
        return "redirect:/projects/" + projectId;
    }

    @PostMapping("/update/{taskId}/{projectId}")
    public String updateTask(@PathVariable Long taskId,
                             @PathVariable Long projectId,
                             @RequestParam String title,
                             @RequestParam String description,
                             @RequestParam LocalDate deadline,
                             HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/";

        taskService.updateTask(taskId, title, description,deadline);
        return "redirect:/projects/" + projectId;
    }

    @PostMapping("/project/{projectId}/save-order")
    public String saveOrder(@PathVariable Long projectId, @RequestParam("taskOrder")List<Long> taskIds, HttpSession session) {
    User user = (User) session.getAttribute("currentUser");
    if (user == null) return "redirect:/";

    taskService.saveTaskOrder(taskIds);
    return "redirect:/projects/" + projectId;

    }

    // Module 6: Filter endpoints
    @GetMapping("/today")
    public String getTasksToday(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";
        
        LocalDate today = LocalDate.now();
        List<Task> taskList = taskService.getTasksByUserAndDeadline(user.getId(), today);
        
        model.addAttribute("taskList", taskList);
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Hôm nay");
        model.addAttribute("filterType", "today");
        
        return "tasks-filtered";
    }

    @GetMapping("/upcoming")
    public String getTasksUpcoming(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";
        
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        List<Task> taskList = taskService.getTasksByUserAndDeadlineRange(user.getId(), today.plusDays(1), nextWeek);
        
        model.addAttribute("taskList", taskList);
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Sắp tới");
        model.addAttribute("filterType", "upcoming");
        
        return "tasks-filtered";
    }
}
