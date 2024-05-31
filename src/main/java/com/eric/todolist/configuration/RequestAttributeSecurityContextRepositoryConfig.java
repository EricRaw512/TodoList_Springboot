package com.eric.todolist.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

@Configuration
public class RequestAttributeSecurityContextRepositoryConfig {

    @Bean
    RequestAttributeSecurityContextRepository requestAttributeSecurityContextRepository() {
    	return new RequestAttributeSecurityContextRepository();
    }
}
