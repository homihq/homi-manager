package com.homihq.manager.project;

import com.homihq.manager.project.domain.Project;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public String showProjects(Model model) {


        return "projects/list";
    }

    @PostMapping("/save")
    public String save(@Valid CreateProjectForm createProjectForm, BindingResult bindingResult, Model model){
        model.addAttribute(createProjectForm);

        if (bindingResult.hasErrors()) {

            log.info("Form validation error");
            model.addAttribute("errorKey", "project.save.failed");
            return "projects/new";
        }

        Project project = this.projectService.save(createProjectForm.name, createProjectForm.description);
        model.addAttribute("successKey", "project.save.success");

        return "projects/new";

    }

    @GetMapping("/new")
    public String showNewProject(Model model) {

        model.addAttribute(new CreateProjectForm());

        return "projects/new";
    }

    @Data
    private static class CreateProjectForm{
        private String name;
        private String description;
    }
}
