package com.revature.controller;

import java.sql.SQLException;

import com.revature.models.Planet;
import com.revature.models.User;
import com.revature.service.PlanetService;
import org.slf4j.LoggerFactory;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;

import io.javalin.http.Context;

public class PlanetController {

	public static Logger logger = LoggerFactory.getLogger(PlanetController.class);

	private PlanetService pService = new PlanetService();

	public void getAllPlanets(Context ctx){
		
		try{
			ctx.json(pService.getAllPlanets()).status(200);
		} catch (SQLException e){
			ctx.json("INVALID REQUEST").status(404);
			logger.error("INVALID REQUEST");
		}
	}

	public void getPlanetByName(Context ctx){
		
		try{
			User u = ctx.sessionAttribute("user");
			String planetName = ctx.pathParam("name");
			
			Planet p = pService.getPlanetByName(u.getUsername(), planetName);
			
			ctx.json(p).status(200);
		} catch (SQLException e){
			ctx.json("INVALID PLANET NAME: CHECK SPELLING").status(404);
			logger.error("INVALID PLANET NAME: CHECK SPELLING");
		}
	}

	public void getPlanetByID(Context ctx) {
		
		try{
			User u = ctx.sessionAttribute("user");
			int planetId = ctx.pathParamAsClass("id", Integer.class).get();
			
			Planet p = pService.getPlanetById(u.getUsername(), planetId);
			
			ctx.json(p).status(200);
		} catch (SQLException e){
			ctx.json("INVALID PLANET ID").status(404);
			logger.error("INVALID PLANET ID");
		}
	}


	public void createPlanet(Context ctx){
		
		try{
			Planet planetToBeCreated = ctx.bodyAsClass(Planet.class);
			User u = ctx.sessionAttribute("user");
			
			Planet createdPlanet = pService.createPlanet(u.getUsername(),planetToBeCreated);

			ctx.json(createdPlanet).status(201);
		} catch (SQLException e){
			ctx.result("INVALID ENTRY: DUPLICATE MIGHT ALREADY EXIST").status(404);
			logger.error("INVALID ENTRY: DUPLICATE MIGHT ALREADY EXIST");
		}
	}

	public void deletePlanet(Context ctx){
		
		try{
			int planetId = ctx.pathParamAsClass("id", Integer.class).get();

			pService.deletePlanetById(planetId);
			ctx.json("DELETION SUCCESSFUL").status(200);
		} catch (SQLException e){
			ctx.json("DELETION FAILED: ITEM MAY NOT EXIST").status(404);
			logger.error("DELETION FAILED: ITEM MAY NOT EXIST");
		}
	}

	public void dropPlanetTable(Context ctx){
		try{
			pService.dropPlanetTable();
			ctx.json("TABLE SUCCESSFULLY DROPPED").status(200);
		} catch(SQLException e){
			ctx.json("TABLE DROP FAILURE").status(400);
			logger.error("TABLE DROP FAILURE");
		}
	}

	public void createPlanetTable(Context ctx){
		try{
			pService.createPlanetTable();
			ctx.json("TABLE SUCCESSFULLY CREATED").status(200);
		} catch(SQLException e){
			ctx.json("TABLE CREATION FAILURE").status(400);
			logger.error("TABLE CREATION FAILURE");
		}
	}
}
