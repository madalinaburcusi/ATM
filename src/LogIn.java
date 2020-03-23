import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.*;

public class LogIn {
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_RESET = "\u001B[0m";

    CheckInput check = new CheckInput();

    public boolean logIn(String userName, String PIN, MongoCollection<Document> credentials) throws IOException {
        if(!check.isUserPinValid(userName, PIN, credentials))
        {
            return false;
        }
        else
        {
            System.out.println();
            System.out.println(ANSI_GREEN + "LogIn Succeed!\n" + ANSI_RESET);
            return true;
        }
    }
}
