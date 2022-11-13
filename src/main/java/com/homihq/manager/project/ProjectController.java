package com.homihq.manager.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/projects")
public class ProjectController {

    @GetMapping
    public String showProjects(Model model) {


        return "projects/list";
    }
}
