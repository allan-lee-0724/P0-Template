package com.revature;

import java.io.IOException;

import com.revature.controller.RequestMapping;

import io.javalin.Javalin;

public class MainDriver {
	
	public static void main(String[] args) throws IOException, InterruptedException{
		Javalin app = Javalin.create(confg ->{
			confg.plugins.enableDevLogging();
		});
		RequestMapping.setupEndpoints(app);
		app.start(7000);
	}
	
}
