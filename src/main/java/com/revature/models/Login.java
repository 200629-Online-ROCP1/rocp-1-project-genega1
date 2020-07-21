package com.revature.models;

public class Login {
	
	public String username;
	public String password; 
	
	public boolean equals(Object o) {
		if (o == this) { 
            return true; 
        } 
		if (!(o instanceof Login)) { 
            return false; 
        } 
		Login other = (Login) o;
		
		if (this.username.equals(other.username) && this.password.equals(other.password)) {
			return true;
		}
		else {
			return false;
		}
	}

}
