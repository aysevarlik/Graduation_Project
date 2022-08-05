package com.aysevarlik.satisfactionsurveyproject.security;

import com.aysevarlik.satisfactionsurveyproject.Bean.PasswordEncoderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Security extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/css/**","/login").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/customer/export/excel").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/api/v1/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().logoutSuccessUrl("/logout").invalidateHttpSession(true);

        http.csrf().ignoringAntMatchers(String.valueOf(HttpMethod.POST),"/customer/save").disable();
        http.csrf().ignoringAntMatchers("/h2-console/**").and().headers().frameOptions().sameOrigin();

    }

    @Autowired
    PasswordEncoderBean encoderBean;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder managerBuilder) throws Exception{
        PasswordEncoder passwordEncoder= PasswordEncoderFactories.createDelegatingPasswordEncoder();
        managerBuilder
                .inMemoryAuthentication()
                .withUser("Ayse")
                .password( encoderBean.passwordEncoder()
                        .encode("root")).roles("USER");

    }
}
