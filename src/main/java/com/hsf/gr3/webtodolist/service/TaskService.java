package com.hsf.gr3.webtodolist.service;

import com.hsf.gr3.webtodolist.entity.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    List<Task> getTasksByProject(Long projectId);
    public Task addTask(Long projectId, String title, String description, Long userId, LocalDate deadline);
    void toggleStatus(Long taskId);
    void deleteTask(Long taskId);
    Task getTaskById(Long taskId);
    Task updateTask(Long taskId, String title, String description, LocalDate deadline);
    public void saveTaskOrder(List<Long> taskIds);
}
