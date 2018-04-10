package com.mannu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class SpringContext {

	@Bean(name="wp")
	public WorkPage createWorkPage() {
		return new WorkPage();
	}
	
	 @Bean
	    public static PropertySourcesPlaceholderConfigurer setUp() {
	        return new PropertySourcesPlaceholderConfigurer();
	    }
	
}

