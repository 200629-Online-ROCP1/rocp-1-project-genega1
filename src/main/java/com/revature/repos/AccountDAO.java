package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.revature.models.Account;
import com.revature.models.AccountStatus;
import com.revature.models.AccountType;
import com.revature.models.Role;
import com.revature.models.TransactionDTO;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class AccountDAO implements IAccountDAO {

	@Override
	public boolean insertAccount(Account account) {

		try (Connection conn = ConnectionUtil.getConnection()) {

			if (account.getAccountId() != 0) {
				return false;
			}
			int index = 1;
			String sql = "INSERT INTO ACCOUNTS ( balance, status_id, type_id, user_id) " + "VALUES ( ?, ?, ?, ?)";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setDouble(index++, account.getBalance());
			statement.setInt(index++, account.getStatus().getStatusId());
			statement.setInt(index++, account.getType().getTypeId());
			statement.setInt(index++, account.getUserId());

			statement.execute();

			return true;

		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public Account getAccountFromAccountID(int accountId) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			int index = 1;
			String sql = "SELECT * FROM ACCOUNTS WHERE account_id = ?";

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(index, accountId);
//			System.out.println(statement);

			ResultSet result = statement.executeQuery();
			Account account = new Account();

			while (result.next()) {
				account.setAccountId(result.getInt("account_id"));
				account.setBalance(result.getDouble("balance"));
				int status_id = result.getInt("status_id");
				account.setStatus(AccountStatus.createFromID(status_id));
				int type_id = result.getInt("type_id");
				account.setType(AccountType.createTypeFromId(type_id));
				account.setUserId(result.getInt("user_id"));
			}

			return account;

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public boolean withdraw(TransactionDTO withdraw, double balance) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			double newBalance = balance - withdraw.amount;

			int index = 1;
			String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setDouble(index++, newBalance);
			statement.setInt(index++, withdraw.accountId);
			statement.execute();
			return true;

		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public boolean deposit(TransactionDTO deposit, double balance) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			double newBalance = balance + deposit.amount;

			int index = 1;
			String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setDouble(index++, newBalance);
			statement.setInt(index++, deposit.accountId);
			statement.execute();
			return true;

		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public Account updateAccount(Account account, Account origin) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			int index = 1;
			String sql = "UPDATE accounts SET balance=?, status_id=?, type_id=?, user_id=? WHERE account_id=?";
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setDouble(index++, account.getBalance());
			statement.setInt(index++, account.getStatus().getStatusId());
			statement.setInt(index++, account.getType().getTypeId());
			statement.setInt(index++, account.getUserId());
			statement.setInt(index++, account.getAccountId());

			statement.execute();

			return getAccountFromAccountID(account.getUserId());

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public int getAccountCount() {
		try (Connection conn = ConnectionUtil.getConnection()) {

			int index = 1;
			String sql = "SELECT COUNT(account_id) FROM accounts";

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
	public Account[] getAccounts() {
		Account[] accounts;

		// Find number of users
		int accountCount = this.getAccountCount();
		accounts = new Account[accountCount];

		try (Connection conn = ConnectionUtil.getConnection()) {

			int index = 1;
			String sql = "SELECT * FROM accounts";

			PreparedStatement statement = conn.prepareStatement(sql);

			ResultSet result = statement.executeQuery();
			int i = 0;
			while (result.next()) {

				accounts[i] = new Account();
				accounts[i].setAccountId(result.getInt("account_id"));
				accounts[i].setBalance(result.getDouble("balance"));
				
				accounts[i].setStatus( AccountStatus.createFromID(result.getInt("status_id")) );
				accounts[i].setType( AccountType.createTypeFromId(result.getInt("type_id")) );
				
				accounts[i].setUserId(result.getInt("user_id"));

				


				i++;
			}

			return accounts;

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public Account getAccountsById(int id) {
		Account a = new Account();

		try (Connection conn = ConnectionUtil.getConnection()) {

			int index = 1;
			String sql = "SELECT * FROM accounts WHERE account_id=?";

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(index++, id);
			ResultSet result = statement.executeQuery();

			while (result.next()) {

				a.setAccountId(result.getInt("account_id"));
				a.setBalance(result.getDouble("balance"));
				
				a.setStatus( AccountStatus.createFromID(result.getInt("status_id")) );
				a.setType( AccountType.createTypeFromId(result.getInt("type_id")) );
				
				a.setUserId(result.getInt("user_id"));

			}

			return a;

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}
	
	private int getNumRecordsByStatus(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			int index = 1;
			String sql = "SELECT COUNT(account_id) FROM accounts WHERE status_id=?";

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(index++, id);
			ResultSet result = statement.executeQuery();

			result.next();
			int retrievedValue = result.getInt("count");

			return retrievedValue;

		} catch (SQLException e) {
			System.out.println(e);
			return 0;
		}
	}
	
	private int getNumRecordsByUser(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			int index = 1;
			String sql = "SELECT COUNT(account_id) FROM accounts WHERE user_id=?";

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(index++, id);
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
	public Account[] findAccountsByStatus(int id) {
		Account[] accounts;


		accounts = new Account[getNumRecordsByStatus(id)];

		try (Connection conn = ConnectionUtil.getConnection()) {

			int index = 1;
			String sql = "SELECT * FROM accounts WHERE status_id=?";

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(index++, id);
			ResultSet result = statement.executeQuery();
			int i = 0;
			while (result.next()) {

				accounts[i] = new Account();
				accounts[i].setAccountId(result.getInt("account_id"));
				accounts[i].setBalance(result.getDouble("balance"));
				
				accounts[i].setStatus( AccountStatus.createFromID(result.getInt("status_id")) );
				accounts[i].setType( AccountType.createTypeFromId(result.getInt("type_id")) );
				
				accounts[i].setUserId(result.getInt("user_id"));

				


				i++;
			}

			return accounts;

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public Account[] findAccountsByUser(int id) {
		Account[] accounts;

		accounts = new Account[getNumRecordsByUser(id)];

		try (Connection conn = ConnectionUtil.getConnection()) {

			int index = 1;
			String sql = "SELECT * FROM accounts WHERE user_id=?";

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(index++, id);
			ResultSet result = statement.executeQuery();
			int i = 0;
			while (result.next()) {

				accounts[i] = new Account();
				accounts[i].setAccountId(result.getInt("account_id"));
				accounts[i].setBalance(result.getDouble("balance"));
				
				accounts[i].setStatus( AccountStatus.createFromID(result.getInt("status_id")) );
				accounts[i].setType( AccountType.createTypeFromId(result.getInt("type_id")) );
				
				accounts[i].setUserId(result.getInt("user_id"));

				


				i++;
			}

			return accounts;

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

}
