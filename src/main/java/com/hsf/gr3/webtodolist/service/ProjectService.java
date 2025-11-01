package com.hsf.gr3.webtodolist.service;

import com.hsf.gr3.webtodolist.entity.Project;

import java.util.List;

public interface ProjectService {

    public List<Project> getAllProjectsByUserId(Long userId);

    public Project getProjectById(Long projectId);

    public Project createProject(Project project);

    public Project updateProject(Project project);

    public void deleteProject(Long projectId);
}
