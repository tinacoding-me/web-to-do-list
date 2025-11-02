package com.hsf.gr3.webtodolist.controller;

import com.hsf.gr3.webtodolist.entity.User;
import com.hsf.gr3.webtodolist.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/add/{projectId}")
    public String addTask(@PathVariable Long projectId,
                          @RequestParam String title,
                          @RequestParam String description,
                          HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/";
        taskService.addTask(projectId, title, description, user.getId());
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
                             HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/";

        taskService.updateTask(taskId, title, description);
        return "redirect:/projects/" + projectId;
    }

}
