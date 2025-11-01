package com.hsf.gr3.webtodolist.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false, columnDefinition = "nvarchar(50)")
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "nvarchar(1000)")
    private String description;

    @Column(name = "created_at",  nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Task(String title, String description, User user, Project project) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.project = project;
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
