package com.revature.service;

import java.sql.SQLException;
import java.util.List;

import com.revature.models.Planet;
import com.revature.repository.PlanetDao;

public class PlanetService {

	private PlanetDao dao;

	public PlanetService(){
		this.dao = new PlanetDao();
	}

	public List<Planet> getAllPlanets() throws SQLException{
		return this.dao.getAllPlanets();
	}

	public Planet getPlanetByName(String owner, String planetName) throws SQLException{
		return this.dao.getPlanetByName(owner, planetName);
	}

	public Planet getPlanetById(String username, int planetId) throws SQLException{
		return this.dao.getPlanetById(username, planetId);
	}

	public Planet createPlanet(String username, Planet p) throws SQLException{
		return this.dao.createPlanet(username, p);
	}

	public void deletePlanetById(int planetId) throws SQLException{
		this.dao.deletePlanetById(planetId);
	}

	public void dropPlanetTable() throws SQLException{
		this.dao.dropPlanetTable();
	}

	public void createPlanetTable() throws SQLException{
		this.dao.createPlanetTable();
	}
}

