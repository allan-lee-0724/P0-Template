package com.revature.controller;

import java.sql.SQLException;
import java.util.List;

import com.revature.models.Moon;
import com.revature.models.User;
import com.revature.service.MoonService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import io.javalin.http.Context;

public class MoonController {

	public static Logger logger = LoggerFactory.getLogger(PlanetController.class);
	
	private MoonService mService = new MoonService();

	public void getAllMoons(Context ctx) throws SQLException{
		try{
			ctx.json(mService.getAllMoons()).status(200);
		} catch (SQLException e){
			ctx.status(404);
			ctx.result("INVALID REQUEST");
			logger.error("INVALID REQUEST");
		}
	}

	public void getMoonByName(Context ctx){
		
		try{
			User u = ctx.sessionAttribute("user");
			String moonName = ctx.pathParam("name");
			
			Moon m = mService.getMoonByName(u.getUsername(), moonName);
			
			ctx.json(m).status(200);
		} catch (SQLException e){
			ctx.status(404);
			ctx.result("INVALID MOON NAME: CHECK SPELLING");
			logger.error("INVALID MOON NAME");
		}
	}

	public void getMoonById(Context ctx){
		
		try{
			User u = ctx.sessionAttribute("user");
			int moonId = ctx.pathParamAsClass("id", Integer.class).get();
			
			Moon m = mService.getMoonById(u.getUsername(), moonId);
			
			ctx.json(m).status(200);
		} catch (SQLException e){
			ctx.status(404);
			ctx.result("INVALID MOON ID");
			logger.error("INVALID MOON NAME");
		}
	}

	public void createMoon(Context ctx) {
		
		try{
			Moon m = ctx.bodyAsClass(Moon.class);
			User u = ctx.sessionAttribute("user");
			
			Moon outGoingMoon = mService.createMoon(u.getUsername(),m);
			
			ctx.json(outGoingMoon).status(201);
		} catch (SQLException e){
			ctx.status(404);
			ctx.result("INVALID ENTRY: DUPLICATE MIGHT ALREADY EXIST");
			logger.error("INVALID ENTRY: DUPLICATE MIGHT ALREADY EXIST");
		}
	}

	public void deleteMoon(Context ctx){
		
		try{
			int moonId = ctx.pathParamAsClass("id", Integer.class).get();

			mService.deleteMoonById(moonId);
			ctx.json("DELETION SUCCESSFUL").status(200);
		} catch (SQLException e){
			ctx.json("DELETION FAILED: ITEM MAY NOT EXIST").status(404);
			logger.error("DELETION FAILED: ITEM MAY NOT EXIST");
		}
		
	}
	
	public void getPlanetMoons(Context ctx) {
		
		try{
			int planetId = ctx.pathParamAsClass("id", Integer.class).get();
		
			List<Moon> moonList = mService.getMoonsFromPlanet(planetId);

			ctx.json(moonList).status(200);
		} catch (SQLException e){
			ctx.status(404);
			ctx.result("INVALID REQUEST");
			logger.error("INVALID REQUEST");
		}
		
		
	}

	public void dropMoonTable(Context ctx){
		try{
			mService.dropMoonTable();
			ctx.json("TABLE SUCCESSFULLY DROPPED").status(200);
		} catch(SQLException e){
			ctx.json("TABLE DROP FAILURE").status(400);
			logger.error("TABLE DROP FAILURE");
		}
	}

	public void createMoonTable(Context ctx){
		try{
			mService.createMoonTable();
			ctx.json("TABLE SUCCESSFULLY CREATED").status(200);
		} catch(SQLException e){
			ctx.json("TABLE CREATION FAILURE").status(400);
			logger.error("TABLE CREATION FAILURE");
		}
	}
}
