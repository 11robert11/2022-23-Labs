package com.company._001.pkg.Messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Message {
	private static final Logger logger = LoggerFactory.getLogger("Message");
	String author;
	String subject;
	String body;
	int id;
	ArrayList<Message> children;
	// Default Constructor
	public Message() {
		this.author = "";
		this.subject = "";
		this.body = "";
		this.id = -1;
		this.children = new ArrayList<>();
	}
	
	// Parameterized Constructor
	public Message(String auth, String subj, String bod, int i) {
		this.author = auth;
		this.subject = subj;
		this.body = bod;
		this.id = i;
		this.children = new ArrayList<>();
	}

	// This function is responsbile for printing the Message
	// (whether Topic or Reply), and all of the Message's "subtree" recursively:

	// After printing the Message with indentation n and appropriate format (see output details),
	// it will invoke itself recursively on all of the Replies inside its childList, 
	// incrementing the indentation value at each new level.

	// Note: Each indentation increment represents 2 spaces. e.g. if indentation ==  1, the reply should be indented 2 spaces, 
	// if it's 2, indent by 4 spaces, etc. 
	public void print(int indentation){
		if(author.equals("") && subject.equals("") && body.equals(""))	{
			logger.warn("Nothing to print!");
		}
	}

	// Default function for inheritance
	public boolean isReply(){
		return false;
	}

	// Returns the subject String
	public String getSubject(){
		 return subject;
	} 

	// Returns the ID
	public int getId(){
		return id;
	}

	// Adds a child pointer to the parent's childList.
	public void addChild(Message child){
		children.add(child);
	}

}
