package com.revature.repos;
import java.util.Set;
import java.util.List;

import com.revature.models.Login;
import com.revature.models.User;

public interface IUserDAO {
	
	public Login getLoginCredentials(Login l);
	
	

	public User getUserFromLogin(Login l);



	public boolean insert(User other);



	public int getUserCount();



	public User[] getUsers();



	public User getUsersById(int id);



	public User updateUser(User other, User origin);

}
