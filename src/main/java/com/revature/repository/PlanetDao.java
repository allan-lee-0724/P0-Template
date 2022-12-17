package com.revature.repository;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.List;

import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;


public class PlanetDao {
    
	/*
	 * Added the throws clause to the method signature because the alternative is to return an empty
	 * arraylist, but the method could succeed with no planets returned, so this is not an ideal solution.
	 * Instead we will let the service layer and/or API handle the exception being thrown
	 */
    public List<Planet> getAllPlanets() throws SQLException{ 
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "select * from planets";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			List<Planet> planets = new ArrayList<Planet>();

			while(rs.next()){ // the resultset next method returns a boolean, so we can use it our loop
				Planet planet = new Planet();
				planet.setId(rs.getInt(1));
				planet.setName(rs.getString(2));
				planet.setOwnerId(rs.getInt(3));

				planets.add(planet);

			}

			return planets;

		} 
	}

	public Planet getPlanetByName(String owner, String planetName) throws SQLException{
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "select * from planets where name = ?";
			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setString(1, planetName);

			ResultSet rs = ps.executeQuery();
			rs.next();

			Planet planet = new Planet();
			planet.setId(rs.getInt(1));
			planet.setName(rs.getString(2));
			planet.setOwnerId(rs.getInt(3));

			return planet;	
			
		} 
	}

	public Planet getPlanetById(String username, int planetId) throws SQLException{
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "select * from planets where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setInt(1, planetId);

			ResultSet rs = ps.executeQuery();
			rs.next();

			Planet planet = new Planet();
			planet.setId(planetId);
			planet.setName(rs.getString(2));
			planet.setOwnerId(rs.getInt(3));

			return planet;

		}
	}

	public Planet createPlanet(String username, Planet p) throws SQLException{
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "insert into planets values (default, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, p.getName());
			ps.setInt(2, p.getOwnerId());

			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();

			Planet newPlanet = new Planet();
			newPlanet.setId(rs.getInt(1));
			newPlanet.setName(rs.getString(2));
			newPlanet.setOwnerId(rs.getInt(3));

			return newPlanet;	

		} 
	}

	public void deletePlanetById(int planetId) throws SQLException{
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "delete from planets where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
		}
	}
}

	