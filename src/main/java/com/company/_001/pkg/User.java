package com.company._001.pkg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.io.*;

public class User {

	private final Logger logger = LoggerFactory.getLogger("User");

	private String USERNAME;
	private String PASSWORD;

	// Creates a User with empty name and password.
	public User() {

	}

	// Creates a User with given username and password.
	public User(String username, String password) {
		logger.info("Creating User: " + username + "" );
		USERNAME = username;
		PASSWORD = password;
	}

	// Returns the username
	public String getUsername(){
		return USERNAME;
	}

	// Returns true if the stored username/password matches the parameters. Otherwise returns false.
	// Note that, even with a User with empty name and password, this is actually a valid User object (it is the default User), 
	// This function must still return false if given an empty username string.  
	public boolean check(String usr, String psd){
		logger.info("Checking Username and Password of " + USERNAME);
		return usr.equals(USERNAME) && psd.equals(PASSWORD);
	}

	// Sets a new password.
	// This function should only set the password if the current (old) password is passed in.
	// Also, a default User cannot have its password changed. 
	// Return true if password changed, return false if not.
	public boolean setPassword(String oldPass, String newPass){
		if (oldPass.equals(PASSWORD))	{
			PASSWORD = newPass;
			return true;
		}
		return false;
	}
}
