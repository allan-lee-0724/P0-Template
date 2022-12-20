package com.revature.repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.utilities.ConnectionUtil;

public class UserDao {
    
    public User getUserByUsername(String username) throws SQLException{
        try(Connection connection = ConnectionUtil.createConnection()){
            String sql = "select * from users where username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            rs.next();
            
            User user = new User();
            user.setId(rs.getInt(1));
            user.setUsername(rs.getString(2));
            user.setPassword(rs.getString(3));

            return user;
        } 
    }

    public User createUser(UsernamePasswordAuthentication registerRequest) throws SQLException{
        try(Connection connection = ConnectionUtil.createConnection()){
            String sql = "insert into users values (default, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, registerRequest.getUsername());
            ps.setString(2, registerRequest.getPassword());

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();

            User newUser = new User();
            rs.next();
            int newId = rs.getInt("id");
            newUser.setId(newId);
            newUser.setUsername(registerRequest.getUsername());
            newUser.setPassword(registerRequest.getPassword());

            return newUser;

        }
    }

    public void createUserTable() throws SQLException{
        try(Connection connection = ConnectionUtil.createConnection()){
            String sql = "create table users(id serial primary key, username varchar(20) unique, password varchar(20))";
            Statement statement = connection.createStatement();
			int tableAffected = statement.executeUpdate(sql);
        }
    }

    public void createPlanetTable() throws SQLException{
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "create table planets(id serial primary key, name varchar(20), ownerId int references users(id) on delete cascade)";
			Statement statement = connection.createStatement();
			int tableAffected = statement.executeUpdate(sql);
		}
	}

    public void dropUser(String username) throws SQLException{
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "delete from users where username = ?; alter sequence users_id_seq restart with 1";
			PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);

            ps.execute();
		} 
	}
}
