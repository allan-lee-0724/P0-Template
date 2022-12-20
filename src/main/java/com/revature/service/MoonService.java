package com.revature.service;

import java.sql.SQLException;
import java.util.List;

import com.revature.models.Moon;
import com.revature.repository.MoonDao;

public class MoonService {

	private MoonDao dao;

	public MoonService(){
		this.dao = new MoonDao();
	}

	public List<Moon> getAllMoons() throws SQLException{
		return this.dao.getAllMoons();
	}

	public Moon getMoonByName(String username, String moonName) throws SQLException{
		return this.dao.getMoonByName(username, moonName);
	}

	public Moon getMoonById(String username, int moonId) throws SQLException{
		return this.dao.getMoonById(username, moonId);
	}

	public Moon createMoon(String username, Moon m) throws SQLException{
		return this.dao.createMoon(username, m);
	}

	public void deleteMoonById(int moonId) throws SQLException{
		this.dao.deleteMoonById(moonId);
	}

	public List<Moon> getMoonsFromPlanet(int planetId) throws SQLException{
		return this.dao.getMoonsFromPlanet(planetId);
	}

	public void dropMoonTable() throws SQLException{
		this.dao.dropMoonTable();
	}

	public void createMoonTable() throws SQLException{
		this.dao.createMoonTable();
	}
}
