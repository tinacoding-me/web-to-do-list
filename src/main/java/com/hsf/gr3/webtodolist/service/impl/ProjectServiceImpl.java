package com.hsf.gr3.webtodolist.service.impl;

import com.hsf.gr3.webtodolist.entity.Project;
import com.hsf.gr3.webtodolist.repository.ProjectRepo;
import com.hsf.gr3.webtodolist.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepo projectRepo;

    @Override
    public List<Project> getAllProjectsByUserId(Long userId) {
        return projectRepo.findAllByUserId(userId);
    }

    @Override
    public Project getProjectById(Long projectId) {
        return projectRepo.findById(projectId).orElse(null);
    }

    @Override
    public Project createProject(Project project) {
        return projectRepo.save(project);
    }

    @Override
    public Project updateProject(Project project) {
        return projectRepo.save(project);
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepo.deleteById(projectId);
    }

}
