package com.revature.repos;
import java.util.Set;

import com.revature.models.Account;
import com.revature.models.TransactionDTO;


public interface IAccountDAO {


	public boolean insertAccount(Account account);
	
	public Account getAccountFromAccountID(int accountId);

	public boolean withdraw(TransactionDTO withdraw, double balance);

	public boolean deposit(TransactionDTO deposit, double balance);

	public Account updateAccount(Account account, Account origin);

	public int getAccountCount();

	public Account[] getAccounts();

	public Account getAccountsById(int id);

	public Account[] findAccountsByStatus(int id);

	public Account[] findAccountsByUser(int id);
	
	
}
