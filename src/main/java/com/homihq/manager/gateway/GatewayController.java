package com.homihq.manager.gateway;

import com.homihq.manager.product.Product;
import com.homihq.manager.product.ProductRepository;
import com.homihq.manager.product.RegionRepository;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/gateways")
public class GatewayController {

    private final RegionRepository regionRepository;
    private final ProductRepository productRepository;

    private final GatewayService gatewayService;

    private final ModelMapper modelMapper;

    @GetMapping
    public String showProjects(Model model) {


        return "gateways/list";
    }
    @PostMapping("/save")
    public String save(@Valid CreateGatewayForm createGatewayForm, BindingResult bindingResult, Model model){
        model.addAttribute(createGatewayForm);
        fillUpGatewayModel(model);

        if (bindingResult.hasErrors()) {

            log.info("Form validation error");
            model.addAttribute("errorKey", "gateway.save.failed");
            return "gateways/new";
        }

        this.gatewayService.save(modelMapper.map(createGatewayForm, GatewayService.CreateGatewayCommand.class));
        model.addAttribute("successKey", "gateway.save.success");

        return "gateways/new";

    }

    private void fillUpGatewayModel(Model model) {
        model.addAttribute(this.regionRepository.findAll());
        List<Product> productList = productRepository.findAll();

        model.addAttribute("dbList", productList.stream().filter(p -> p.getId().longValue() == 1).findFirst().get().getVariants());
        model.addAttribute("containerList", productList.stream().filter(p -> p.getId().longValue() == 2).findFirst().get().getVariants());
    }
    @GetMapping("/new")
    public String createNewGateway(
            Model model) {
        model.addAttribute(new CreateGatewayForm());
        fillUpGatewayModel(model);

        return "gateways/new";
    }

    @Data
    private static class CreateGatewayForm{
        @NotBlank(message = "Name is required.")
        private String name;
        private String description;

        @Positive(message = "Region is required.")
        private Integer regionId;

        @Positive(message = "Cache is required.")
        private Integer dbId;

        private boolean standbyInstance;

        @Positive(message = "Container is required.")
        private Integer containerId;

        @Min(value = 1, message = "Minimum of 1 container is required.")
        @Max(value = 15, message = "Maximum of 15 containers can be used.")
        private int noOfInstances = 1;

        @NotBlank(message = "DigitalOcean project is required.")
        private String doProjectId;

        @NotBlank(message = "DigitalOcean token is required.")
        private String doTokenId;
    }
}
