package com.hsf.gr3.webtodolist.service.impl;

import com.hsf.gr3.webtodolist.entity.Project;
import com.hsf.gr3.webtodolist.entity.Task;
import com.hsf.gr3.webtodolist.entity.User;
import com.hsf.gr3.webtodolist.repository.ProjectRepo;
import com.hsf.gr3.webtodolist.repository.TaskRepo;
import com.hsf.gr3.webtodolist.repository.UserRepo;
import com.hsf.gr3.webtodolist.service.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        return (project != null) ? taskRepo.findByProjectOrderByPositionAsc(project) : List.of();
    }

    @Override
    public Task addTask(Long projectId, String title, String description, Long userId, LocalDate deadline) {
        Project project = projectRepo.findById(projectId).orElse(null);
        User user = userRepo.findById(userId).orElse(null);
        if (project == null || user == null) return null;
        Task lastTask = taskRepo.findTopByProjectOrderByPositionDesc(project);
        Integer position;
        if (lastTask == null){
            position = 1;
        }else {
            position = lastTask.getPosition() + 1;
        }
        Task task = new Task(title, description, user, project, position, deadline);
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

    @Override
    public Task getTaskById(Long taskId) {
        return taskRepo.findById(taskId).orElse(null);
    }

    @Override
    public Task updateTask(Long taskId, String title, String description, LocalDate deadline) {
        Task task = taskRepo.findById(taskId).orElse(null);
        if (task != null) {
            task.setTitle(title);
            task.setDescription(description);
            task.setDeadline(deadline);
            taskRepo.save(task);
        }
        return task;
    }

    @Transactional
    @Override
    public void saveTaskOrder(List<Long> taskIds) {
        for (int i = 0; i < taskIds.size(); i++) {
            Long taskId = taskIds.get(i);
            Task task = taskRepo.findById(taskId).orElse(null);
            if (task != null) {
                task.setPosition(i + 1);
                taskRepo.save(task);
            }
        }
    }

}
