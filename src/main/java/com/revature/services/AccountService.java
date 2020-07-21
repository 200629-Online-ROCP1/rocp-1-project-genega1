package com.revature.services;

import com.revature.models.Account;
import com.revature.models.TransactionDTO;
import com.revature.repos.AccountDAO;
import com.revature.repos.IAccountDAO;
import com.revature.repos.IUserDAO;
import com.revature.repos.UserDAO;

public class AccountService {
	private final IAccountDAO dao = new AccountDAO();

	public boolean submitAccount(Account account) {
		return dao.insertAccount(account);
	}
	
	public Account getAccountFromAccountID(int accountID) {
		return dao.getAccountFromAccountID(accountID);
	}

	public boolean withdraw(TransactionDTO withdraw, double balance) {
		// TODO Auto-generated method stub
		return dao.withdraw(withdraw, balance);
	}
	
	public boolean deposit(TransactionDTO deposit, double balance) {
		// TODO Auto-generated method stub
		return dao.deposit(deposit, balance);
	}

	public Account updateAccount(Account account, Account origin) {
		// TODO Auto-generated method stub
		return dao.updateAccount(account, origin);
	}

	public int getAccountCount() {
		// TODO Auto-generated method stub
		return dao.getAccountCount();
	}

	public Account[] getAccounts() {
		// TODO Auto-generated method stub
		return dao.getAccounts();
	}

	public Account getAccountsById(int id) {
		// TODO Auto-generated method stub
		return dao.getAccountsById(id);
	}

	public Account[] findAccountsByStatus(int id) {
		return dao.findAccountsByStatus(id);
	}
	
	public Account[] findAccountsByUser(int id) {
		return dao.findAccountsByUser(id);
	}

}
