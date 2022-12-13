package com.revature.repository;

import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.sql.ResultSet;
import java.util.List;

import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;
import com.revature.models.User;
import com.revature.repository.UserDao;

public class PlanetDao {
    
    public List<Planet> getAllPlanets() {
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "select * from planets";
			PreparedStatement ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			boolean hasNext = rs.next();

			List<Planet> planets = new LinkedList<Planet>();
			while(hasNext == true){
				Planet planet = new Planet();
				planet.setId(rs.getInt(1));
				planet.setOwnerId(rs.getInt(2));
				planet.setName(rs.getString(3));

				planets.add(planet);
				hasNext = rs.next();
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
			if(user.getUsername().equals("")){
				throw new IOException();
			} else{
				int userId = user.getId();
				String sql = "select * from planets where ownId = ? and name = ?";
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
			}
			
		} catch (SQLException | IOException e){
			System.out.println(e.getMessage());
			return new Planet();
		}
	}

	public Planet getPlanetById(String username, int planetId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Planet createPlanet(String username, Planet p) {
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "insert into planets values (default, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			UserDao ud = new UserDao();
			User user = new User();

			user = ud.getUserByUsername(username);
			if(user.getUsername().equals("")){
				throw new IOException();
			} else{
				p.setOwnerId(user.getId());
				ps.setInt(1, p.getOwnerId());
				ps.setString(2, p.getName());

				ps.execute();
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();

				Planet newPlanet = new Planet();
				int newId = rs.getInt("id");
				newPlanet.setId(newId);
				newPlanet.setOwnerId(p.getOwnerId());
				newPlanet.setName(p.getName());

				return newPlanet;

			}
		} catch(SQLException | IOException e){
			System.out.println(e.getMessage());
			return new Planet();
		}
	}

	public void deletePlanetById(int planetId) {
		// TODO Auto-generated method stub
	}
}
