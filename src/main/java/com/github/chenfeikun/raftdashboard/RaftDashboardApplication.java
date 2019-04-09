package com.github.chenfeikun.raftdashboard;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RaftDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(RaftDashboardApplication.class, args);
	}

//    @Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			for (String name : beanNames) {
//				System.out.println("bean ----- " + name);
//			}
//		};
//	}
}
