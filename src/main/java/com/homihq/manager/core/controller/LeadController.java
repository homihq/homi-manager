package com.homihq.manager.core.controller;

import com.homihq.manager.core.LeadService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import javax.validation.constraints.Email;


@Controller
@Slf4j
@RequiredArgsConstructor
public class LeadController {

    private final LeadService quickLeadService;

    @PostMapping("/waitlist")
    public String save(@Valid QuickLeadForm quickLeadForm , Model model) {
        log.info("Saving quick lead - {}", quickLeadForm);

        quickLeadService.save(quickLeadForm.email);

        model.addAttribute("success","Thank you for joining the wait list. " +
                "You will be informed as soon as the sign up starts in early 2023.");
        model.addAttribute(new QuickLeadForm());
        return "quicklead :: quickleadform";
    }

    @Data
    private static class QuickLeadForm {


        @Email(message = "Email is not valid.")
        String email;


    }
}
