package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.revature.models.Login;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class UserDAO implements IUserDAO {

	IUserRoleDAO urdao = new UserRoleDAO();
	
	@Override
	public Login getLoginCredentials(Login l) {

		// System.out.println("In login");

		try (Connection conn = ConnectionUtil.getConnection()) {

			int index = 1;
			String sql = "SELECT password FROM USERS WHERE username = ?";

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(index, l.username);
//			System.out.println(statement);

			ResultSet result = statement.executeQuery();
			String retrievedPassword = null;

			while (result.next()) {
				retrievedPassword = result.getString("password");

			}
			Login o = new Login();
			o.username = l.username;
			o.password = retrievedPassword;
			return o;


		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}

	}

	@Override
	public User getUserFromLogin(Login l) {

		try (Connection conn = ConnectionUtil.getConnection()) {

			int index = 1;
			String sql = "SELECT * FROM USERS WHERE username = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(index, l.username);
			ResultSet result = statement.executeQuery();
			User user = new User();
			String retrievedValue = null;

			while (result.next()) {
				int retrievedInt = result.getInt("user_id");

				user.setUserId(retrievedInt);

				retrievedValue = result.getString("username");
				user.setUsername(retrievedValue);
				retrievedValue = result.getString("password");
				user.setPassword(retrievedValue);
				retrievedValue = result.getString("first_name");
				user.setFirstName(retrievedValue);
				retrievedValue = result.getString("last_name");
				user.setLastName(retrievedValue);
				retrievedValue = result.getString("email");
				user.setEmail(retrievedValue);
				
				
				retrievedInt = result.getInt("role_id");
				
				Role r = urdao.findById(retrievedInt);
				
				user.setRole(r);

			}
			return user;

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public boolean insert(User user) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			if (user.getUserId() != 0) {
				return false;
			}
			int index = 1;
			String sql = "INSERT INTO USERS ( username, password, first_name, last_name, email, role_id) "
					+ "VALUES ( ?, ?, ?, ?, ?, ?)";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(index++, user.getUsername());
			statement.setString(index++, user.getPassword());
			statement.setString(index++, user.getFirstName());
			statement.setString(index++, user.getLastName());
			statement.setString(index++, user.getEmail());
			statement.setInt(index++, user.getRole().getRoleId());

			statement.execute();

			return true;

		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public int getUserCount() {
		try (Connection conn = ConnectionUtil.getConnection()) {

			int index = 1;
			String sql = "SELECT COUNT(user_id) FROM users";

			PreparedStatement statement = conn.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			result.next();
			int retrievedValue = result.getInt("count");

			return retrievedValue;

		} catch (SQLException e) {
			System.out.println(e);
			return 0;
		}
	}

	@Override
	public User[] getUsers() {
		User[] users;

		// Find number of users
		int userCount = this.getUserCount();
		users = new User[userCount];

		try (Connection conn = ConnectionUtil.getConnection()) {

			int index = 1;
			String sql = "SELECT * FROM users";

			PreparedStatement statement = conn.prepareStatement(sql);

			ResultSet result = statement.executeQuery();
			int i = 0;
			while (result.next()) {

				users[i] = new User();
				users[i].setUserId(result.getInt("user_id"));
				users[i].setUsername(result.getString("username"));
				users[i].setPassword(result.getString("password"));
				users[i].setFirstName(result.getString("first_name"));
				users[i].setLastName(result.getString("last_name"));
				users[i].setEmail(result.getString("email"));
				
				
				Role r = urdao.findById(result.getInt("role_id"));
				users[i].setRole(r);

				i++;
			}

			return users;

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}

	}

	@Override
	public User selectUser(int id) {
		User u = new User();

		try (Connection conn = ConnectionUtil.getConnection()) {

			int index = 1;
			String sql = "SELECT * FROM users WHERE user_id=?";

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(index++, id);
			ResultSet result = statement.executeQuery();

			while (result.next()) {

				u.setUserId(result.getInt("user_id"));
				u.setUsername(result.getString("username"));
				u.setPassword(result.getString("password"));
				u.setFirstName(result.getString("first_name"));
				u.setLastName(result.getString("last_name"));
				u.setEmail(result.getString("email"));
				
				
				Role r = urdao.findById(result.getInt("role_id"));
				u.setRole(r);

			}

			return u;

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public User update(User other, User origin) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			int index = 1;
			String sql = "UPDATE users SET ";
			if (!other.getUsername().equals(origin.getUsername())) {
				sql += " username=?, ";
			}
			sql += "  password=?, first_name=?, last_name=?, ";
			if (!other.getEmail().equals(origin.getEmail())) {
				sql += " email=?, ";
			}
			sql += " role_id=? WHERE user_id = ?";
			
			//System.out.println(other.getUserId());

			PreparedStatement statement = conn.prepareStatement(sql);
			
			if (!other.getUsername().equals(origin.getUsername())) {
			statement.setString(index++, other.getUsername());
			}
			
			statement.setString(index++, other.getPassword());
			statement.setString(index++, other.getFirstName());
			statement.setString(index++, other.getLastName());
			
			if (!other.getEmail().equals(origin.getEmail())) {
			statement.setString(index++, other.getEmail());
			}
			
			statement.setInt(index++, other.getRole().getRoleId());
			statement.setInt(index++, other.getUserId());
			
			statement.execute();



			return selectUser(other.getUserId());

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

}
