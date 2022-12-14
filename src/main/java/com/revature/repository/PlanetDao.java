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
import com.revature.models.User;


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

		} catch(SQLException e){
			System.out.println(e.getMessage());
			return List.of();
		}
	}

	public Planet getPlanetByName(String owner, String planetName) {
		try(Connection connection = ConnectionUtil.createConnection()){
			UserDao ud = new UserDao();
			User user = ud.getUserByUsername(owner);
			
			int userId = user.getId();
			String sql = "select * from planets where ownerId = ? and name = ?";
			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setInt(1, userId);
			ps.setString(2, planetName);

			ResultSet rs = ps.executeQuery();
			rs.next();

			Planet planet = new Planet();
			planet.setId(rs.getInt(1));
			planet.setOwnerId(userId);
			planet.setName(planetName);

			return planet;	
			
			
		} catch (SQLException e){
			System.out.println(e.getMessage());
			return new Planet();
		}
	}

	public Planet getPlanetById(String username, int planetId) {
		try(Connection connection = ConnectionUtil.createConnection()){
			UserDao ud = new UserDao();
			User user = ud.getUserByUsername(username);

			int userId = user.getId();
			String sql = "select * from planets where ownerId = ? and id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setInt(1, user.getId());
			ps.setInt(2, planetId);

			ResultSet rs = ps.executeQuery();
			rs.next();

			Planet planet = new Planet();
			planet.setId(planetId);
			planet.setName(rs.getString(2));
			planet.setOwnerId(userId);

			return planet;


		}catch(SQLException e){
			System.out.println(e.getMessage());
			return new Planet();
		}
	}

	public Planet createPlanet(String username, Planet p) {
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "insert into planets values (default, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			UserDao ud = new UserDao();
			User user = new User();

			user = ud.getUserByUsername(username);
			
			p.setOwnerId(user.getId());
			ps.setString(1, p.getName());
			ps.setInt(2, p.getOwnerId());

			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();

			Planet newPlanet = new Planet();
			int newId = rs.getInt("id");
			newPlanet.setId(newId);
			newPlanet.setOwnerId(p.getOwnerId());
			newPlanet.setName(p.getName());

			return newPlanet;	

			
		} catch(SQLException e){
			System.out.println(e.getMessage());
			return new Planet();
		}
	}

	public void deletePlanetById(int planetId) {
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "delete from planets where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
			int rowsAffected = ps.executeUpdate();
			System.out.println("Rows affected: " + rowsAffected);
		} catch(SQLException e){
			System.out.println(e.getMessage()); // good spot to add some logging?
		}
	}


	public static void main(String[] args) {
		PlanetDao planetDao = new PlanetDao();
		planetDao.deletePlanetById(4);
		try{
			System.out.println(planetDao.getAllPlanets());
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}

	