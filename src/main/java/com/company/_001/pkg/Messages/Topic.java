package com.company._001.pkg.Messages;
import com.company._001.pkg.Messages.Message;

public class Topic extends Message {

	// Default Constructor
	public Topic() {

	}

	// Parameterized constructor
	public Topic(String auth, String subj, String bod, int i) {
		super(auth, subj, bod, i);
	}

	// Returns if it's a reply (false)
	public boolean isReply(){
		return false;
	}
}
