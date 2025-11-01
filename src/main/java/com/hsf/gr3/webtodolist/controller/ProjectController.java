package com.hsf.gr3.webtodolist.controller;

import com.hsf.gr3.webtodolist.entity.Project;
import com.hsf.gr3.webtodolist.entity.User;
import com.hsf.gr3.webtodolist.service.ProjectService;
import com.hsf.gr3.webtodolist.service.impl.ProjectServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectServiceImpl projectService;

    @GetMapping()
    public String listProjects(HttpSession httpSession, Model model) {
        User user = (User) httpSession.getAttribute("currentUser");
        if(user == null) return "redirect:/";
        List<Project> projects = projectService.getAllProjectsByUserId(user.getId());
        model.addAttribute("projects", projects);
        model.addAttribute("user", user);

        model.addAttribute("project", new Project());
        model.addAttribute("user", user);
        return "projects";
    }

    @PostMapping("/new")
    public String addProject(@ModelAttribute("project") Project project,
                            HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("currentUser");
        if(user == null) {return "redirect:/";}

        project.setUser(user);
        projectService.createProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/{id}")
    public String viewProject(@PathVariable("id") Long id,
                              HttpSession httpSession,
                              Model model) {
        User user = (User) httpSession.getAttribute("currentUser");
        if(user == null) {return "redirect:/";}
        model.addAttribute("user", user);

        Project project = projectService.getProjectById(id);
        if(project==null){
            model.addAttribute("error","Dự án không tồn tại!");
        }
        if(project!=null&&project.getUser().getId()!=user.getId()){
            model.addAttribute("error","Bạn không có quyền truy cập dự án này!");
        }

        model.addAttribute("project", project);
        return "project-tasks";
    }

    @PostMapping("/edit/{id}")
    public String updateProject(@PathVariable("id") Long id,
                                @ModelAttribute("project") Project updatedProject,
                                HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("currentUser");
        if(user == null) {return "redirect:/";}

        Project existingProject = projectService.getProjectById(id);
        if(existingProject==null || existingProject.getUser().getId()!=user.getId()){
            return "redirect:/projects";
        }

        existingProject.setName(updatedProject.getName());
        existingProject.setDescription(updatedProject.getDescription());
        projectService.updateProject(existingProject);
        return "redirect:/projects/" + id;
    }

    @PostMapping("/delete/{id}")
    public String deleteProject(@PathVariable("id") Long projectId,
                                HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("currentUser");
        if(user == null) {return "redirect:/";}

        Project project = projectService.getProjectById(projectId);
        if(project==null || project.getUser().getId()!=user.getId()){
            return "redirect:/projects";
        }

        projectService.deleteProject(projectId);
        return "redirect:/projects";
    }
}
