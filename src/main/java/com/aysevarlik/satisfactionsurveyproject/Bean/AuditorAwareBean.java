package com.aysevarlik.satisfactionsurveyproject.Bean;


import com.aysevarlik.satisfactionsurveyproject.AuditorAware.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditorAwareBean {

    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditorAwareImpl();
    }
}
