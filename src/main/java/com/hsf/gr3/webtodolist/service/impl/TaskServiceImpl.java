package com.hsf.gr3.webtodolist.service.impl;

import com.hsf.gr3.webtodolist.entity.Project;
import com.hsf.gr3.webtodolist.entity.Task;
import com.hsf.gr3.webtodolist.entity.User;
import com.hsf.gr3.webtodolist.repository.ProjectRepo;
import com.hsf.gr3.webtodolist.repository.TaskRepo;
import com.hsf.gr3.webtodolist.repository.UserRepo;
import com.hsf.gr3.webtodolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public List<Task> getTasksByProject(Long projectId) {
        Project project = projectRepo.findById(projectId).orElse(null);
        return (project != null) ? taskRepo.findByProject(project) : List.of();
    }

    @Override
    public Task addTask(Long projectId, String title, String description, Long userId) {
        Project project = projectRepo.findById(projectId).orElse(null);
        User user = userRepo.findById(userId).orElse(null);
        if (project == null || user == null) return null;

        Task task = new Task(title, description, user, project);
        return taskRepo.save(task);
    }

    @Override
    public void toggleStatus(Long taskId) {
        Task task = taskRepo.findById(taskId).orElse(null);
        if (task != null) {
            task.setCompleted(!task.isCompleted());
            taskRepo.save(task);
        }
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepo.deleteById(taskId);
    }
}
