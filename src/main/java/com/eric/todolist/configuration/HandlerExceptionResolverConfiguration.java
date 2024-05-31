package com.eric.todolist.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@Configuration
public class HandlerExceptionResolverConfiguration {
	
	@Bean 
	ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver() {
		return new ExceptionHandlerExceptionResolver();
	}
}
