package com.company._001.pkg.Messages;
import com.company._001.pkg.Messages.Message;

public class Reply extends Message {

	// Default Constructor
	public Reply() {

	}

	// Parameterized Constructor
	public Reply(String auth, String subj, String bod, int i) {
		
	}

	// Returns if this is a reply (true)
	public boolean isReply(){
		return true;
	}
}
