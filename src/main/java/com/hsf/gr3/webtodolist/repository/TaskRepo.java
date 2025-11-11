package com.hsf.gr3.webtodolist.repository;

import com.hsf.gr3.webtodolist.entity.Project;
import com.hsf.gr3.webtodolist.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<Task,Long> {
    List<Task> findByProjectOrderByPositionAsc(Project project);
    Task findTopByProjectOrderByPositionDesc(Project project);
    Task findTaskById(Long id);

    Task getTaskById(long id);
}
