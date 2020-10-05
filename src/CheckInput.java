import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import static com.mongodb.client.model.Filters. *;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class CheckInput {
    final String ANSI_RESET = "\u001B[0m";
    final String RED_BOLD = "\033[1;31m";
    int numberOfPinTrials = 0;
    String patternIBAN = "[A-Z]{2} ?\\d{2} ?[A-Z]{4} ?\\d{4} ?\\d{4} ?\\d{4} ?[\\d]{0,2} ?[A-Z]{0,3}";

    public boolean userNameExists(String user, MongoCollection<Document> credentials) {
        Document myDoc = credentials.find(eq("userName", user.toUpperCase())).first();
        boolean userExists = false;

        //Check if the username already exists
        if(myDoc != null) {
                System.out.println(RED_BOLD + "\nUser name already exists." + ANSI_RESET);
                userExists =  true;
            }

        return userExists;
    }

    public boolean isUserPinValid(String user, String pin, MongoCollection<Document> credentials) throws IOException, NoSuchProviderException, NoSuchAlgorithmException {
        boolean userValid = false;
        boolean pinValid = false;

        //Check if the username already exists
        Document myDoc = credentials.find(eq("userName", user.toUpperCase())).first();
        ObjectMapper objectMapper = new ObjectMapper();

        if(myDoc != null) {
            userValid = true;
            String jsonUserDetails = myDoc.toJson();
            JsonNode jsonNode = objectMapper.readTree(jsonUserDetails);
            String jsonPIN = jsonNode.get("PIN").asText();
            if(jsonPIN.equals(EncryptPass.getHash(pin,user, credentials)))
            {
                pinValid = true;
            }
        }

        if (!userValid || !pinValid) {
            numberOfPinTrials++;
            return false;
        }else {
            return true;
        }

    }

    public boolean isValidIBAN(String IBAN) {
            if (IBAN.matches(patternIBAN)) {
                return true;
            }
         else{
            System.out.println(RED_BOLD + "Not a valid IBAN." + ANSI_RESET);
            return false;
        }
    }

    public boolean isValidPin(String pin){
        try {
            int PIN = Integer.parseInt(pin);
                if (pin.length() != 4 || PIN<0) {
                    System.out.println(RED_BOLD + "Not a valid PIN. 4 digits required." + ANSI_RESET);
                    return false;
                }
                    return true;
            } catch (Exception e) {
                System.out.println(RED_BOLD + "Not a valid PIN. 4 digits required." + ANSI_RESET);
                return false;
            }
    }

    public boolean isValidLoginCode(String option){
        try{
            int code = Integer.parseInt(option);
            if(code <1 || code >3)
            {
                System.out.println(RED_BOLD + "Not a valid code." + ANSI_RESET);
                return false;
            }else {
                return true;
            }

        }catch(Exception e)
        {
            System.out.println(RED_BOLD + "Not a valid code." + ANSI_RESET);
            return false;
        }

    }

    public boolean isValidMenuCode(String option){
        try{
            int code = Integer.parseInt(option);
            if(code <1 || code >8)
            {
                System.out.println(RED_BOLD + "Not a valid code." + ANSI_RESET);
                return false;
            }else {
                return true;
            }

        }catch(Exception e)
        {
            System.out.println(RED_BOLD + "Not a valid code." + ANSI_RESET);
            return false;
        }

    }

    public boolean isValidLongInput(String input){
        try {
            long amount = Long.parseLong(input);
            if(amount<0)
            {
                System.out.println(RED_BOLD + "Not a valid amount." + ANSI_RESET);
                return false;
            }
            return true;
        }catch(Exception e){
            System.out.println(RED_BOLD + "Not a valid amount." + ANSI_RESET);
            return false;
        }
    }

    public boolean isValidDoubleInput(String input){
        try {
            double amount = Double.parseDouble(input);
            if(amount<=0)
            {
                System.out.println(RED_BOLD + "Not a valid amount." + ANSI_RESET);
                return false;
            }
            return true;
        }catch(Exception e){
            System.out.println(RED_BOLD + "Not a valid amount." + ANSI_RESET);
            return false;
        }
    }

    public boolean isValidProviderCode(String option){
        try{
            int code = Integer.parseInt(option);
            if(code <1 || code >3)
            {
                System.out.println(RED_BOLD + "Not a valid provider code." + ANSI_RESET);
                return false;
            }else {
                return true;
            }

        }catch(Exception e)
        {
            System.out.println(RED_BOLD + "Not a valid provider code." + ANSI_RESET);
            return false;
        }

    }




}
