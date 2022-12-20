package com.revature.controller;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.service.UserService;

import io.javalin.http.Context;

public class AuthenticateController {
	
	public static Logger logger = LoggerFactory.getLogger(PlanetController.class);
	private UserService userService = new UserService();

	public void authenticate(Context ctx) {
		
		UsernamePasswordAuthentication loginRequest = ctx.bodyAsClass(UsernamePasswordAuthentication.class);
		
		try{
			User u = userService.getUserByUsername(loginRequest.getUsername());
	
			if (u != null && u.getPassword().equals(loginRequest.getPassword())) {
				ctx.sessionAttribute("user", u);
				ctx.status(200);
			} else {
				ctx.status(400);
			}
		} catch (SQLException e){
			ctx.status(404);
			ctx.result("ENTRY NOT FOUND");
			logger.error("ENTRY NOT FOUND");
		}
	}

	public void register(Context ctx) throws SQLException{
		/*
		 * There is no code to handle something going wrong, like accidentally trying to create an 
		 * account with a username that is already taken. Be aware of this, don't worry about handling that
		 * problem
		 */


		UsernamePasswordAuthentication registerRequest = ctx.bodyAsClass(UsernamePasswordAuthentication.class);

		User newUser = userService.register(registerRequest);

		ctx.json(newUser).status(201);
	}

	public void invalidateSession(Context ctx) {
		ctx.req().getSession().invalidate();
	}
	
	public boolean verifySession(Context ctx) {	
		return ctx.sessionAttribute("user") != null;
	}

	public void dropUser(Context ctx){
		UsernamePasswordAuthentication loginInfo = ctx.bodyAsClass(UsernamePasswordAuthentication.class);
		try{
			userService.dropUser(loginInfo.getUsername());
			ctx.json("USER SUCCESSFULLY DROPPED").status(200);
		} catch(SQLException e){
			ctx.json("USER DROP FAILURE").status(400);
			logger.error("USER DROP FAILURE");
		}
	}

	public void createUserTable(Context ctx){
		try{
			userService.createUserTable();
			ctx.json("TABLE SUCCESSFULLY CREATED").status(200);
		} catch(SQLException e){
			ctx.json("TABLE CREATION FAILURE").status(400);
			logger.error("TABLE CREATION FAILURE");
		}
	}
}
