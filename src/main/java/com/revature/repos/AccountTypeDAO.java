package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.AccountType;
import com.revature.models.Role;
import com.revature.util.ConnectionUtil;

public class AccountTypeDAO implements IAccountTypeDAO{

	@Override
	public AccountType findById(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			AccountType at = new AccountType();
			int index = 1;
			String sql = "SELECT * FROM account_type WHERE type_id = ?";

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(index, id);
			ResultSet result = statement.executeQuery();
			

			while (result.next()) {
				at.setType(result.getString("account_type"));
				at.setTypeId(result.getInt("type_id"));
			}

			return at;

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

}
