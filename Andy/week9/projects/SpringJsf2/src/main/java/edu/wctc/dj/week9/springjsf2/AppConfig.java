package edu.wctc.dj.week9.springjsf2;

import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	public AppConfig() {
		System.out.println("AppConfig");
	}
	
	@PostConstruct
	public void postConstruct() {
		System.out.println("postConstruct");
	}

}
