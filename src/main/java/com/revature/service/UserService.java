package com.revature.service;

import java.sql.SQLException;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.UserDao;

public class UserService {

	private UserDao dao;

	public UserService(){
		this.dao = new UserDao();
	}

	public User getUserByUsername(String username) throws SQLException{
		/*
		 * All this service method needs to do is return the data grabbed by the dao object.
		 * That's it: the other parts of the application will handle interpreting what to do with
		 * the user info returned by this particular method
		 */
		return this.dao.getUserByUsername(username);
	}

	public User register(UsernamePasswordAuthentication registerRequest) throws SQLException{
		return this.dao.createUser(registerRequest);
	}

	public void createUserTable() throws SQLException{
		this.dao.createUserTable();
	}

	public void dropUser(String username) throws SQLException{
		this.dao.dropUser(username);
	}

}
