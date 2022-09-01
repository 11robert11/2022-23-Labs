package com.company._001;

import com.company._001.pkg.BBoard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger("Main");
    private static final String DEFAULT_USERS = "src/main/resources/001/users.txt";

    public static void main(String[] args) throws IOException {
        BBoard bBoard = new BBoard("A Bulletin Board", System.in);
        if(args.length >= 1) {
            logger.info("Loading User Given User File");
            bBoard.loadUsers(args[0]);
        }
        else {
            logger.info("Loading Default Users File");
            bBoard.loadUsers(DEFAULT_USERS);

        }
        bBoard.run();
    }
}