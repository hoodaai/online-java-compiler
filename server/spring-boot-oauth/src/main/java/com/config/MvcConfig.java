package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuration for MCV
 */
@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/web/home").setViewName("home");
        registry.addViewController("/web").setViewName("home");
        registry.addViewController("/web/hello").setViewName("hello");
        registry.addViewController("/web/login").setViewName("login");
        registry.addViewController("/403").setViewName("forbidden");
        registry.addViewController("/web/register").setViewName("register");
    }
}
