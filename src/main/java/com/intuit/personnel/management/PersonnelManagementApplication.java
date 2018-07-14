package com.intuit.personnel.management;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.intuit.personnel.management")
@SpringBootApplication
public class PersonnelManagementApplication {
	 
	public static void main(String[] args) {
		 ApplicationContext ctx = SpringApplication.run(PersonnelManagementApplication.class, args);
	        String[] beanNames = ctx.getBeanDefinitionNames();
	         
	        Arrays.sort(beanNames);
	         
	        for (String beanName : beanNames)
	        {
	            System.out.println(beanName);
	        }
	}
}
