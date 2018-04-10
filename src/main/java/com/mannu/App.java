package com.mannu;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
	
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringContext.class);
		WorkPage wp=(WorkPage) context.getBean("wp");
		wp.setArgs(args);
	}

}
