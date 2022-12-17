package com.revature;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Map;

import javax.management.RuntimeErrorException;

import com.revature.controller.RequestMapping;

import io.javalin.Javalin;

public class MainDriver {
	
	public static void main(String[] args) throws SQLException, IOException, InterruptedException{
		Javalin app = Javalin.create(confg ->{
			confg.plugins.enableDevLogging();
		});
		RequestMapping.setupEndpoints(app);
		app.start(7000);

		// ProcessBuilder pb = new ProcessBuilder();
		// pb.command("cmd.exe", "/c", "dir C:\\Users\\hongy\\Desktop\\P0-Template");
		// String[] command = {"cat", "C:\\Users\\hongy\\Desktop\\P0-Template\\average-latency-success-evaluator.sh"};
		// System.out.println(pb.command(command));

		// Process p = pb.start();
		// StringBuilder sb = new StringBuilder();
		// BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		// String line;

		// while ((line = br.readLine()) != null){
		// 	sb.append(line + "\n");
		// }

		// int exitVal = p.waitFor();
		// if(exitVal == 0){
		// 	System.out.println("Success!");
		// 	System.out.println(sb);
		// 	System.exit(0);
		// }
	}
	
}
