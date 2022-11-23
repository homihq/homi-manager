package com.homihq.manager.project;

import com.homihq.manager.project.domain.Project;
import com.homihq.manager.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public Project save(String name, String description) {
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);

        return this.projectRepository.save(project);
    }
}
