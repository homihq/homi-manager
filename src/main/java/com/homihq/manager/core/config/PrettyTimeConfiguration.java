package com.homihq.manager.core.config;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrettyTimeConfiguration {

    @Bean
    public PrettyTime prettyTime(){
        return new PrettyTime();
    }
}
