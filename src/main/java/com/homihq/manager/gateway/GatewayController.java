package com.homihq.manager.gateway;

import com.homihq.manager.cloud.CloudGatewayPlanRepository;
import com.homihq.manager.cloud.CloudProviderRepository;
import com.homihq.manager.cloud.CloudRegionRepository;
import com.homihq.manager.project.repository.ProjectRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/gateways")
public class GatewayController {

    private final CloudProviderRepository cloudProviderRepository;
    private final CloudRegionRepository cloudRegionRepository;

    private final ProjectRepository projectRepository;
    private final CloudGatewayPlanRepository cloudGatewayPlanRepository;
    private final GatewayService gatewayService;

    private final ModelMapper modelMapper;

    @GetMapping
    public String showProjects(Model model) {


        return "gateways/list";
    }
    @PostMapping("/save")
    public String save(@Valid CreateGatewayForm createGatewayForm, BindingResult bindingResult, Model model){
        model.addAttribute(createGatewayForm);


        if (bindingResult.hasErrors()) {

            log.info("Form validation error");
            model.addAttribute("errorKey", "gateway.save.failed");
            model.addAttribute(this.cloudProviderRepository.findAllByActive(true));
            model.addAttribute(this.cloudRegionRepository.findAll());
            model.addAttribute(this.projectRepository.findAll()); //TODO filter by user role and project where user is admin
            model.addAttribute(cloudGatewayPlanRepository.findAllByActive(true));
            return "gateways/new";
        }

        this.gatewayService.save(modelMapper.map(createGatewayForm, GatewayService.CreateGatewayCommand.class));
        model.addAttribute(this.cloudProviderRepository.findAllByActive(true));
        model.addAttribute(this.cloudRegionRepository.findAll());
        model.addAttribute(this.projectRepository.findAll()); //TODO filter by user role and project where user is admin
        model.addAttribute(cloudGatewayPlanRepository.findAllByActive(true));
        model.addAttribute("successKey", "gateway.save.success");

        return "gateways/new";

    }
    @GetMapping("/new")
    public String showNewProject(Model model) {
        model.addAttribute(new CreateGatewayForm());
        model.addAttribute(this.cloudProviderRepository.findAllByActive(true));
        model.addAttribute(this.cloudRegionRepository.findAll());
        model.addAttribute(this.projectRepository.findAll()); //TODO filter by user role and project where user is admin
        model.addAttribute(cloudGatewayPlanRepository.findAllByActive(true));
        return "gateways/new";
    }

    @Data
    private static class CreateGatewayForm{
        @NotBlank(message = "Name is required")
        private String name;
        private String description;
        @Positive(message = "Cloud service provider is required")
        private Long cloudProviderId;
        @Positive(message = "Cloud region is required")
        private Integer cloudRegionId;
        @Positive(message = "Project is required")
        private Long projectId;

        @Positive(message = "Plan is required")
        private Long cloudGatewayPlanId;

    }
}
