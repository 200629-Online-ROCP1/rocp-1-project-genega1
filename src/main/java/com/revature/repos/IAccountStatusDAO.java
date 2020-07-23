package com.revature.repos;

import com.revature.models.AccountStatus;

public interface IAccountStatusDAO {
	public AccountStatus findById(int id);
}
