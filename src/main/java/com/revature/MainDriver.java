package com.revature;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.postgresql.util.PSQLException;

import com.revature.controller.RequestMapping;

import io.javalin.Javalin;

public class MainDriver {
	
	public static void main(String[] args) throws IOException, InterruptedException{
		Javalin app = Javalin.create(confg ->{
			confg.plugins.enableDevLogging();
		});
		RequestMapping.setupEndpoints(app);
		app.start(7000);



		// ProcessBuilder pb = new ProcessBuilder("C:/Program Files/Git/usr/bin/bash", "-c", ". average-latency-success-evaluator.sh");
		// pb.redirectErrorStream(true);
		// Process p = pb.start();
		// IOUtils.copy(p.getInputStream(), System.out);
		// p.waitFor();


		// hashmap key string value actual thing itself
	}
	
}
