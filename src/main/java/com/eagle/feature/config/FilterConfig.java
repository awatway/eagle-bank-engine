package com.eagle.feature.config;

import com.eagle.feature.auth.JwtProvider;
import com.eagle.feature.auth.filter.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
        @Bean
        public FilterRegistrationBean<JwtFilter> jwtFilter(JwtProvider jwtProvider) {
            FilterRegistrationBean<JwtFilter> reg = new FilterRegistrationBean<>();
            reg.setFilter(new JwtFilter(jwtProvider));
            reg.addUrlPatterns("/v1/users/*", "/v1/accounts/*");
            return reg;
        }
}
