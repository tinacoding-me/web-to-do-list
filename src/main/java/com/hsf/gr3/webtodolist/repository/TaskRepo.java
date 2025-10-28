package com.hsf.gr3.webtodolist.repository;

import com.hsf.gr3.webtodolist.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task,Long> {
}
