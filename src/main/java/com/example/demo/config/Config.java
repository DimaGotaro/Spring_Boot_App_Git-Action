package com.example.demo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.servlet.Filter;
import java.util.List;

@Configuration
public class Config {

    @Bean
    public FilterRegistrationBean hiddenHttpMethodFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean =
                new FilterRegistrationBean<>(new HiddenHttpMethodFilter());
        filterFilterRegistrationBean.setUrlPatterns(List.of("/*"));

        return filterFilterRegistrationBean;
    }
}
