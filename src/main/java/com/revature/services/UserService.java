package com.revature.services;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.models.User;
import com.revature.repos.IUserDAO;
import com.revature.repos.UserDAO;

public class UserService {

	private final IUserDAO dao = new UserDAO();
	

	public boolean register(User other) {
		
		return dao.insert(other);
	}


	public int getUserCount() {
		
		return dao.getUserCount();
	}


	public User[] getUsers() {
		return dao.getUsers();
	}


	public User getUsersById(int id) {
		return dao.getUsersById(id);
	}


	public User updateUser(User other, User origin) {
		// TODO Auto-generated method stub
		return dao.updateUser(other, origin);
	}





	
}
