package com.homihq.manager.core.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ControllerErrorHandlerAdvice {

    private final ErrorConfigProperties errorConfigProperties;


    @ModelAttribute
    public void handleRequest(HttpServletRequest request, Model model) {

        log.debug("# Here - {}", request.getRequestURI());

        /*
        String requestURI = request.getRequestURI();
        //counter increment for each access to a particular uri
        counterMap.computeIfAbsent(requestURI, key -> new LongAdder())
                .increment();
        //populating counter in the model
        model.addAttribute("counter", counterMap.get(requestURI).sum());
        //populating request URI in the model
        model.addAttribute("uri", requestURI);

         */
    }


    @ExceptionHandler(value = Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception e) throws Exception {
        log.error("Error", e);
        log.info("errorConfigProperties - {}", errorConfigProperties);
        log.info("Url - {}",  req.getRequestURI());
        log.info("Exception - {}", e.getClass().getSimpleName());

        Optional<ErrorConfigProperties.ErrorConfig> errorConfig =
        errorConfigProperties.getErrorConfigList()
                .stream()
                .filter(ec ->
                    StringUtils.equals(req.getRequestURI(), ec.getUrl())
                            &&
                            StringUtils.equals(e.getClass().getSimpleName(), ec.getException())
                ).findAny();

        ModelAndView mav = new ModelAndView();
        if(errorConfig.isPresent()) {

            mav.addObject("errorKey", errorConfig.get().getMsgKey());

            if(errorConfig.get().getAttributes() != null && !errorConfig.get().getAttributes().isEmpty()) {
                for(ErrorConfigProperties.ErrorConfig.Attribute attribute : errorConfig.get().getAttributes()){
                    mav.addObject(attribute.getKey(), attribute.getValue());
                }
            }

            mav.setViewName(errorConfig.get().getView());
        }
        else{
            mav.addObject("errorCode", "ERR - 001 : User Already exists");
            mav.setViewName("500");
        }

        return mav;
    }




}
