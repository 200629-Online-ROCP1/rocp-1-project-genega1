package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.Account;
import com.revature.models.AccountStatus;
import com.revature.models.AccountType;
import com.revature.models.Role;
import com.revature.util.ConnectionUtil;

public class UserRoleDAO implements IUserRoleDAO {

	@Override
	public Role findById(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			Role r = new Role();
			int index = 1;
			String sql = "SELECT * FROM user_role WHERE role_id = ?";

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(index, id);
			ResultSet result = statement.executeQuery();
			

			while (result.next()) {
				r.setRole(result.getString("user_role"));
				r.setRoleId(result.getInt("role_id"));
			}

			return r;

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

}
