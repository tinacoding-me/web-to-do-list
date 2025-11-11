package com.hsf.gr3.webtodolist.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.hsf.gr3.webtodolist.entity.Task;

public interface TaskService {
    List<Task> getTasksByProject(Long projectId);
    public Task addTask(Long projectId, String title, String description, Long userId, LocalDate deadline);
    void toggleStatus(Long taskId);
    void deleteTask(Long taskId);
    Task getTaskById(Long taskId);
    Task updateTask(Long taskId, String title, String description, LocalDate deadline);
    public void saveTaskOrder(List<Long> taskIds);
    
    // Module 6: Methods for filtering and statistics
    List<Task> getTasksByUserAndDeadline(Long userId, LocalDate deadline);
    List<Task> getTasksByUserAndDeadlineRange(Long userId, LocalDate start, LocalDate end);
    Map<String, Long> getTaskStatistics(Long userId);
}
