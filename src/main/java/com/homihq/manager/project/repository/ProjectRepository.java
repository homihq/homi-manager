package com.homihq.manager.project.repository;

import com.homihq.manager.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
