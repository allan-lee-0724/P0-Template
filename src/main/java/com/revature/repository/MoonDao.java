package com.revature.repository;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.revature.models.Moon;
import com.revature.models.User;
import com.revature.utilities.ConnectionUtil;

public class MoonDao {
    
	public static Logger logger = LoggerFactory.getLogger(MoonDao.class);
	
    public List<Moon> getAllMoons(){
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "select * from moons";
			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(sql);

			List<Moon> moons = new ArrayList<>();

			while(rs.next()){
				Moon moon = new Moon();
				moon.setId(rs.getInt(1));
				moon.setName(rs.getString(2));
				moon.setMyPlanetId(rs.getInt(3));
				
				moons.add(moon);
			}

			return moons;

		} catch(SQLException e){
			System.out.println(e.getMessage());
			return List.of();
		}
	}

	public Moon getMoonByName(String username, String moonName) {
		try(Connection connection = ConnectionUtil.createConnection()){
			UserDao ud = new UserDao();
			User user = ud.getUserByUsername(username);
			
			String sql = "select * from moons where ownerid = ? and name = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, user.getId());
			ps.setString(2, moonName);

			ResultSet rs = ps.executeQuery();
			rs.next();

			Moon moon = new Moon();
			moon.setId(rs.getInt(1));
			moon.setName(moonName);
			moon.setMyPlanetId(rs.getInt(3));

			return moon;
		} catch(SQLException e){
			System.out.println(e.getMessage());
			return new Moon();
		}
	}

	public Moon getMoonById(String username, int moonId) {
		try(Connection connection = ConnectionUtil.createConnection()){
			UserDao ud = new UserDao();
			User user = ud.getUserByUsername(username);
			
			String sql = "select * from moons where ownerid = ? and id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setInt(1, user.getId());
			ps.setInt(2, moonId);

			ResultSet rs = ps.executeQuery();
			rs.next();

			Moon moon = new Moon();
			moon.setId(moonId);
			moon.setName(rs.getString(2));
			moon.setMyPlanetId(rs.getInt(3));

			return moon;
			
		} catch(SQLException e){
			System.out.println(e.getMessage());
			return new Moon();
		}
	}

	public Moon createMoon(String username, Moon m) {
		try(Connection connection = ConnectionUtil.createConnection()){
			UserDao ud = new UserDao();
			User user = ud.getUserByUsername(username);
			
			String sql = "insert into moons values (default, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, m.getName());
			ps.setInt(2, user.getId());

			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();

			Moon newMoon = new Moon();
			newMoon.setId(rs.getInt(1));
			newMoon.setName(rs.getString(2));
			newMoon.setMyPlanetId(rs.getInt(3));

			return newMoon;
			
		} catch(SQLException e){
			System.out.println(e.getMessage());
			return new Moon();
		}
	}

	public void deleteMoonById(int moonId) {
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "delete from moons where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			
			ps.setInt(1, moonId);

			int rowsAffected = ps.executeUpdate();
			System.out.println("Rows affected: " + rowsAffected);

			if(rowsAffected == 0){
				throw new SQLException();
			}
		} catch(SQLException e){
			System.out.println(e.getMessage());
			
			logger.error("Unable to delete Moon by ID: ENTRY NOT FOUND");
		}
	}

	public List<Moon> getMoonsFromPlanet(int planetId){
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "select * from moons where planetid = ?";
			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setInt(1, planetId);

			ResultSet rs = ps.executeQuery();
			List<Moon> moons = new ArrayList<>();

			while(rs.next()){
				Moon moon = new Moon();
				moon.setMyPlanetId(planetId);
				moon.setName(sql);
				moon.setId(planetId);

				moons.add(moon);
			}

			return moons;

		} catch(SQLException e){
			System.out.println(e.getMessage());
			return List.of();
		}
	}
}
