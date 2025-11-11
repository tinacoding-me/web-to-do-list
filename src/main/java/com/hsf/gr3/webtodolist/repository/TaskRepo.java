package com.hsf.gr3.webtodolist.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hsf.gr3.webtodolist.entity.Project;
import com.hsf.gr3.webtodolist.entity.Task;
import com.hsf.gr3.webtodolist.entity.User;

public interface TaskRepo extends JpaRepository<Task,Long> {
    List<Task> findByProjectOrderByPositionAsc(Project project);
    Task findTopByProjectOrderByPositionDesc(Project project);
    Task findTaskById(Long id);

    Task getTaskById(long id);
    
    // Module 6: Query methods for filtering and statistics
    List<Task> findByUserAndDeadlineOrderByPositionAsc(User user, LocalDate deadline);
    List<Task> findByUserAndDeadlineBetweenOrderByPositionAsc(User user, LocalDate start, LocalDate end);
    List<Task> findByUserOrderByPositionAsc(User user);
    long countByUserAndCompleted(User user, boolean completed);
    long countByUser(User user);
}
