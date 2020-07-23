package com.revature.repos;

import com.revature.models.Role;

public interface IUserRoleDAO {
	public Role findById(int id);
}
