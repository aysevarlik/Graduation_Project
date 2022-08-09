package com.aysevarlik.satisfactionsurveyproject.security;

import com.aysevarlik.satisfactionsurveyproject.Bean.PasswordEncoderBean;
import org.springframework.beans.factory.annotation.Autowired;
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
                .antMatchers("/css/**").permitAll()
                .antMatchers("/customer/export/excel").hasAnyRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true);

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
                .password( encoderBean.passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .and()
                .withUser("Enes")
                .password(encoderBean.passwordEncoder().encode("user"))
                .roles("USER");
    }
}
