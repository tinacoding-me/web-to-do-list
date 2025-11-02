package com.hsf.gr3.webtodolist.service;

import com.hsf.gr3.webtodolist.entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> getTasksByProject(Long projectId);
    Task addTask(Long projectId, String title, String description, Long userId);
    void toggleStatus(Long taskId);
    void deleteTask(Long taskId);
}
