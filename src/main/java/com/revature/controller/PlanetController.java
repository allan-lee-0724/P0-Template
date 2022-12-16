package com.revature.controller;

import java.sql.SQLException;
import java.util.List;

import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.models.User;
import com.revature.service.MoonService;
import com.revature.service.PlanetService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import io.javalin.http.Context;
import io.javalin.validation.Validator;

public class PlanetController {
	
	public static Logger logger = LoggerFactory.getLogger(PlanetController.class);

	private PlanetService pService = new PlanetService();

	public void getAllPlanets(Context ctx) throws SQLException{
		
		try{
			ctx.json(pService.getAllPlanets()).status(200);
		} catch (SQLException e){
			logger.error(e.getMessage());
		}
	}

	public void getPlanetByName(Context ctx) throws SQLException{
		
		User u = ctx.sessionAttribute("user");
		String planetName = ctx.pathParam("name");
		
		Planet p = pService.getPlanetByName(u.getUsername(), planetName);
		
		ctx.json(p).status(200);
	}

	public void getPlanetByID(Context ctx) throws SQLException{
		
		User u = ctx.sessionAttribute("user");
		int planetId = ctx.pathParamAsClass("id", Integer.class).get();
		
		Planet p = pService.getPlanetById(u.getUsername(), planetId);
		
		ctx.json(p).status(200);
	}


	public void createPlanet(Context ctx) throws SQLException{
		
		Planet planetToBeCreated = ctx.bodyAsClass(Planet.class);
		User u = ctx.sessionAttribute("user");
		
		Planet createdPlanet = pService.createPlanet(u.getUsername(),planetToBeCreated);

		ctx.json(createdPlanet).status(201);
	}

	public void deletePlanet(Context ctx) throws SQLException{
		
		int planetId = ctx.pathParamAsClass("id", Integer.class).get();
		
		pService.deletePlanetById(planetId);
		ctx.json("Planet successfully deleted").status(202);
	}
}
