package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.AccountStatus;
import com.revature.models.Role;
import com.revature.util.ConnectionUtil;

public class AccountStatusDAO implements IAccountStatusDAO {

	@Override
	public AccountStatus findById(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			AccountStatus r = new AccountStatus();
			int index = 1;
			String sql = "SELECT * FROM account_status WHERE status_id = ?";

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(index, id);
			ResultSet result = statement.executeQuery();
			

			while (result.next()) {
				r.setStatus(result.getString("account_status"));
				r.setStatusId(result.getInt("status_id"));
			}

			return r;

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

}
