package com.homihq.manager.gateway;

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

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/gateways")
public class GatewayController {

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
            return "gateways/new";
        }

        this.gatewayService.save(modelMapper.map(createGatewayForm, GatewayService.CreateGatewayCommand.class));
        model.addAttribute("successKey", "gateway.save.success");

        return "gateways/new";

    }


    @GetMapping("/new")
    public String createNewGateway(
            Model model) {
        model.addAttribute(new CreateGatewayForm());

        return "gateways/new";
    }

    @Data
    private static class CreateGatewayForm{
        @NotBlank(message = "Name is required.")
        private String name;
        private String description;

        private int noOfInstances;
        private String doProjectId;
        private String doTokenId;

        private int deploymentTarget;

    }
}
