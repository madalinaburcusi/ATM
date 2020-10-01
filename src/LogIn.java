import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class LogIn {

    CheckInput check = new CheckInput();

    public boolean logIn(String userName, String PIN, MongoCollection<Document> credentials) throws IOException, NoSuchProviderException, NoSuchAlgorithmException {
        if(!check.isUserPinValid(userName, PIN, credentials))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
