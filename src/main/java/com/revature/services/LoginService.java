package com.revature.services;

import com.revature.models.Login;
import com.revature.models.User;
import com.revature.models.User;
import com.revature.repos.IUserDAO;
import com.revature.repos.UserDAO;

public class LoginService {
	
	private final IUserDAO dao = new UserDAO();
	

	public boolean login(Login l) {
		
		
		return dao.getLoginCredentials(l).equals( l );
		
		
	}
	
	public User getUserFromLogin(Login l) {
		return dao.getUserFromLogin(l);
	}
}
