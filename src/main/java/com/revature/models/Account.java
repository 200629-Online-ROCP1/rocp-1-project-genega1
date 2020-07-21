package com.revature.models;

public class Account {
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public AccountStatus getStatus() {
		return status;
	}
	public void setStatus(AccountStatus status) {
		this.status = status;
	}
	public AccountType getType() {
		return type;
	}
	public void setType(AccountType type) {
		this.type = type;
	}
	private int accountId; // primary key
	private double balance; // not null
	private AccountStatus status;
	private AccountType type;
	private int userId;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

}
