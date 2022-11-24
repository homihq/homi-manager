package com.homihq.manager.gateway;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotBlank;

@Controller
@Slf4j
@RequestMapping("/gateways")
public class GatewayController {

    @GetMapping
    public String showProjects(Model model) {


        return "gateways/list";
    }

    @GetMapping("/new")
    public String showNewProject(Model model) {
        model.addAttribute(new CreateGatewayForm());

        return "gateways/new";
    }

    @Data
    private static class CreateGatewayForm{
        @NotBlank(message = "Name is required")
        private String name;
        private String description;
        @NotBlank(message = "Cloud service provider is required")
        private Integer cloudProvider;
        @NotBlank(message = "Cloud region is required")
        private Integer cloudRegion;
        @NotBlank(message = "Project is required")
        private Long projectId;

    }
}
