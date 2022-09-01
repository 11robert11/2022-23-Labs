package com.company._001.pkg;
import com.company._001.pkg.Messages.Message;
import com.company._001.pkg.Messages.Reply;
import com.company._001.pkg.Messages.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.io.*;

public class BBoard {

	private final Logger logger = LoggerFactory.getLogger("BBoard");

	private String title;
	User currentUser;
	ArrayList<Message> messageList = new ArrayList<Message>();
	ArrayList<User> USERS = new ArrayList<>();
	Scanner scanner;

	public BBoard() {
		logger.info("Creating BBoard with default title.");
		new BBoard("default_BBoard", System.in);
	}

	// Same as the default constructor except it sets the title of the board
	public BBoard(String title, InputStream inputStream) {
		scanner = new Scanner(inputStream);
		logger.info("Creating New BBoard with title: [" + title + "]");
		this.title = title;
		currentUser = null;

	}

	public void loadUsers(String inputFile) throws FileNotFoundException {
		logger.info("Attempting to load [" + inputFile +  "] for users");
		Scanner userReader = new Scanner(new File(inputFile));
		String[] splitUserInfo = new String[2];
		while (userReader.hasNextLine())	{
			splitUserInfo = userReader.nextLine().split(" ");
			USERS.add(new User(splitUserInfo[0], splitUserInfo[1]));
			logger.info("Added User: " + splitUserInfo[0]);

		}
	}

	// Asks for and validates a user/password. 
	// This function asks for a username and a password, then checks the userList ArrayList for a matching User.
	// If a match is found, it sets currentUser to the identified User from the list
	// If not, it will keep asking until a match is found or the user types 'q' or 'Q' as username to quit
	// When the users chooses to quit, sayu "Bye!" and return from the login function
	public void login(){
		System.out.println(title);

		while (true)	{
			System.out.print("Enter your username (Q, or q to quit):");
			String username = scanner.nextLine();
			if(username.toLowerCase(Locale.ROOT).equals("q"))	{
				logger.warn("Exiting");
				System.out.println("Bye Bye");
				System.exit(0);
			}
			System.out.print("Enter your password: ");
			String password = scanner.nextLine();

			for(int i = 0; i < USERS.size(); i++)	{
				if(USERS.get(i).check(username, password))	{
					currentUser = USERS.get(i);
					System.out.println("Welcome " + currentUser.getUsername());
					return;
				}

			}
			System.out.println("Wrong Username and/or password!");
		}
	}
	
	// Contains main loop of Bulletin Board
	// IF and ONLY IF there is a valid currentUser, enter main loop, displaying menu items
	// --- Display Messages ('D' or 'd')
	// --- Add New Topic ('N' or 'n')
	// --- Add Reply ('R' or 'r')
	// --- Change Password ('P' or 'p')
	// --- Quit ('Q' or 'q')
	// With any wrong input, user is asked to try again
	// Q/q should reset the currentUser to 0 and then end return
	// Note: if login() did not set a valid currentUser, function must immediately return without showing menu
	public void run(){
		logger.info("Running Bulletin Board");
		login();
		if(currentUser != null)	{
			String input = "";
			while (!input.toLowerCase(Locale.ROOT).equals("q"))	{
				System.out.println("Menu");
				System.out.println("  - Display Messages ('D' or 'd')");
				System.out.println("  - Add New Topic ('N' or 'n')");
				System.out.println("  - Add New Reply to a Topic ('R' or 'r')");
				System.out.println("  - Change Password ('P' or 'p')");
				System.out.println("  - Quit ('Q' or 'q')");
				System.out.print("Choose an action: ");
				input = scanner.nextLine();
				System.out.println("");
				switch (input.toLowerCase(Locale.ROOT))	{
					case "d":
						display();
						break;
					case "n":
						addTopic();
						break;
					case "r":
						addReply();
						break;
					case "p":
						setPassword();
						break;
					default:
						System.out.println("Ummmm");
				}
				System.out.println("");
			}
		}
	}

	// Traverse the BBoard's message list, and invote the print function on Topic objects ONLY
	// It will then be the responsibility of the Topic object to invoke the print function recursively on its own replies
	// The BBoard display function will ignore all reply objects in its message list
	private void display(){
		if(messageList.size() == 0)	{
			System.out.println("Nothing to Dispaly");
		}

		for(Message message: messageList)	{
			if(!message.isReply())	{
				System.out.println("--------------------------------------------");
				System.out.println(message.getId() + ": ");
				message.print(0);
				System.out.println("--------------------------------------------");

			}
		}
	}


	// This function asks the user to create a new Topic (i.e. the first message of a new discussion "thread")
	// Every Topic includes a subject (single line), and body (single line)

	/* 
	Subject: "Thanks"
	Body: "I love this bulletin board that you made!"
	*/

	// Each Topic also stores the username of currentUser; and message ID, which is (index of its Message + 1)

	// For example, the first message on the board will be a Topic who's index will be stored at 0 in the messageList ArrayList,
	// so its message ID will be (0+1) = 1
	// Once the Topic has been constructed, add it to the messageList
	// This should invoke your inheritance of Topic to Message
	private void addTopic(){
		System.out.println("Subject: ");
		String subject = scanner.nextLine();
		System.out.println("Body: ");
		String body = scanner.nextLine();
		messageList.add(new Topic(currentUser.getUsername(), subject, body, messageList.size() + 1));
	}

	// This function asks the user to enter a reply to a given Message (which may be either a Topic or a Reply, so we can handle nested replies).
	//		The addReply function first asks the user for the ID of the Message to which they are replying;
	//		if the number provided is greater than the size of messageList, it should output and error message and loop back,
	// 		continuing to ask for a valid Message ID number until the user enters it or -1.
	// 		(-1 returns to menu, any other negative number asks again for a valid ID number)
	
	// If the ID is valid, then the function asks for the body of the new message, 
	// and constructs the Reply, pushing back the Reply on to the messageList.
	// The subject of the Reply is a copy of the parent Topic's subject with the "Re: " prefix.
	// e.g., suppose the subject of message #9 was "Thanks", the user is replying to that message:


	/*
			Enter Message ID (-1 for Menu): 9
			Body: It was a pleasure implementing this!
	*/

	// Note: As before, the body ends when the user enters an empty line.
	// The above dialog will generate a reply that has "Re: Thanks" as its subject
	// and "It was a pleasure implementing this!" as its body.

	// How will we know what Topic this is a reply to?
	// In addition to keeping a pointer to all the Message objects in BBoard's messageList ArrayList
	// Every Message (wheather Topic or Reply) will also store an ArrayList of pointers to all of its Replies.
	// So whenever we build a Reply, we must immediately store this Message in the parent Message's list. 
	// The Reply's constructor should set the Reply's subject to "Re: " + its parent's subject.
	// Call the addChild function on the parent Message to push back the new Message (to the new Reply) to the parent's childList ArrayList.
	// Finally, push back the Message created to the BBoard's messageList. 
	// Note: When the user chooses to return to the menu, do not call run() again - just return fro mthis addReply function. 
	private void addReply() {
		//todo this shit proboly dosnt work
		System.out.println("Enter Message ID (-1 for Menu): ");
		int input_id = scanner.nextInt();
		scanner.nextLine();

		while (input_id != -1)	{
			if(input_id > 0 && input_id <= messageList.size())	{
				System.out.println("Body: ");
				String body = scanner.nextLine();
				messageList.add(new Reply(currentUser.getUsername(), messageList.get(input_id - 1).getSubject(), body, messageList.size() + 1));
			}
		}
	}

	// This function allows the user to change their current password.
	// The user is asked to provide the old password of the currentUser.
	// 		If the received password matches the currentUser password, then the user will be prompted to enter a new password.
	// 		If the received password doesn't match the currentUser password, then the user will be prompted to re-enter the password. 
	// 		The user is welcome to enter 'c' or 'C' to cancel the setting of a password and return to the menu.
	// Any password is allowed except 'c' or 'C' for allowing the user to quit out to the menu. 
	// Once entered, the user will be told "Password Accepted." and returned to the menu.
	private void setPassword() {
		String oldPassword;
		while (true)	{
			System.out.println("Old password: ");
			oldPassword = scanner.nextLine();
			if(currentUser.check(currentUser.getUsername(), oldPassword));	{
				System.out.println("Enter your new password: ");
				if(currentUser.setPassword(oldPassword, scanner.nextLine()))	{
					System.out.println("Succesfully Changed");
					return;
				}
				System.out.println("You did something wrong");
			}
		}
		
	}

}
