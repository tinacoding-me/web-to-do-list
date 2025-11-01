package com.hsf.gr3.webtodolist.repository;

import com.hsf.gr3.webtodolist.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project,Long> {
    List<Project> findAllByUserId(long userId);
}
