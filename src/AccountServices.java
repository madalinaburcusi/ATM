import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.LocalDate;
import java.util.Scanner;

import static com.mongodb.client.model.Filters.eq;

public class AccountServices {

    public static final String WHITE_UNDERLINED = "\033[4;37m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String RED_BOLD = "\033[1;31m";
    final String operationCanceled = ANSI_GREEN + "The operation was canceled." + ANSI_RESET;
    final String currency = "RON";

    String service,destination;
    private CheckInput check = new CheckInput();
    private HistorySaver saveToDB = new HistorySaver();
    private Scanner scanner = new Scanner(System.in);
    ObjectMapper objectMapper = new ObjectMapper();

    LocalDate date;

    public String getValidCash(){
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.print(ANSI_GREEN + "Amount:      " + ANSI_RESET);
        input = scanner.nextLine();
        if(input.toUpperCase().equals("CANCEL"))
            input = "0";

        while(!check.isValidLongInput(input)){
            System.out.print(ANSI_GREEN + "Amount:      " + ANSI_RESET);
            input = scanner.nextLine();
            if(input.toUpperCase().equals("CANCEL"))
                input = "0";
        }
        return input;
    }

    public String register(MongoCollection<Document> credentials,MongoCollection<Document> accountDetails) throws NoSuchAlgorithmException, NoSuchProviderException, IOException {
        String IBAN, PIN, userName;

        System.out.println();
        System.out.print("User Name: ");
        userName =  scanner.nextLine().toUpperCase();

        while(check.userNameExists(userName, credentials))
        {
            System.out.println();
            System.out.print("User Name: ");
            userName =  scanner.nextLine().toUpperCase();
        }

        System.out.print("IBAN: ");
        IBAN = scanner.nextLine().toUpperCase();

        while(!check.isValidIBAN(IBAN))
        {
            System.out.println();
            System.out.print("IBAN: ");
            IBAN = scanner.nextLine().toUpperCase();
        }

        System.out.print("PIN: ");
        PIN = scanner.nextLine();

        while(!check.isValidPin(PIN))
        {
            System.out.println();
            System.out.print("PIN: ");
            PIN = scanner.nextLine();
        }

        Document doc = new Document("userName",userName)
                .append("IBAN", IBAN);
        credentials.insertOne(doc);
        EncryptPass.setHash(PIN, userName, credentials);

        //Initialize current balance of the account
        doc = new Document("userName",userName).append("currentBalance", 0);
        accountDetails.insertOne(doc);

        System.out.println();
        System.out.println(ANSI_GREEN + "Your account has been created." + ANSI_RESET);
        System.out.println();
        return userName;
    }

    public double getCurrentBalance(String userName, MongoCollection <Document> accountDetails) {
        String details;
        double currentBalance;

        Document myDoc = accountDetails.find(eq("userName", userName.toUpperCase())).first();
        if(myDoc == null)
        {
            myDoc = new Document("userName",userName.toUpperCase())
                    .append("currentBalance", 0);
            accountDetails.insertOne(myDoc);
            currentBalance = 0;
        }
        else {
            details = myDoc.toJson();
            JsonNode jsonNode = null;
            try {
                jsonNode = objectMapper.readTree(details);
            } catch (IOException e) {
                e.printStackTrace();
            }
            currentBalance = Double.parseDouble(jsonNode.get("currentBalance").asText());
        }
        return currentBalance;
    }

    public void depositCash(String userName, MongoCollection<Document>transactions, MongoCollection <Document> accountDetails) {
        long cash = Long.parseLong(getValidCash());
        if(cash == 0)
        {
            System.out.println(operationCanceled);
        }
        else {
            System.out.println();
            System.out.println("You have deposited " + cash + " " + currency + " in the current account.");

            destination = "debit account";
            service = "deposit";
            saveToDB.saveHistory(userName,date.now().toString(), service, destination, String.valueOf(cash), currency,transactions);

            accountDetails.updateOne(eq("userName", userName.toUpperCase()),
                    new Document("$set", new Document("userName", userName.toUpperCase()).append("currentBalance",
                            getCurrentBalance(userName, accountDetails) + cash)));
        }
    }

    public void withdrawCash(String userName,MongoCollection<Document>transactions,MongoCollection<Document>accountDetails) {
        long cash = Long.parseLong(getValidCash());
        if(cash == 0)
        {
            System.out.println(operationCanceled);
        }
        else if(cash > getCurrentBalance(userName,accountDetails))
            {
            System.out.println(RED_BOLD + "Insufficient founds." + ANSI_RESET);
            }
        else
            {
                System.out.println();
                System.out.println("You have withdrawn " + cash + " " + currency + " from the current account.");

                destination = "cash";
                service = "withdraw";
                saveToDB.saveHistory(userName,date.now().toString(), service, destination,String.valueOf(cash), currency,transactions);

                accountDetails.updateOne(eq("userName", userName.toUpperCase()),
                        new Document("$set", new Document("userName", userName.toUpperCase()).append("currentBalance",
                                getCurrentBalance(userName, accountDetails) - cash)));
            }
    }

    public void payBills(String userName, double amount, Provider provider, MongoCollection<Document> transactions, MongoCollection<Document> accountDetails) {
        if(amount > getCurrentBalance(userName,accountDetails))
        {
            System.out.println("Insufficient founds.");
        }
        else{
            System.out.println();
            System.out.println("You have payed " + String.format("%.2f",amount) + " " + currency + " to " + provider.provider + ".");

            service = "Billing";
            destination =  provider.provider;
            saveToDB.saveHistory(userName,date.now().toString(), service, destination,String.valueOf(amount), currency,transactions);

            accountDetails.updateOne(eq("userName", userName.toUpperCase()),
                    new Document("$set", new Document("userName", userName.toUpperCase()).append("currentBalance",
                            getCurrentBalance(userName, accountDetails) - amount)));

            System.out.println(ANSI_GREEN + "Current Balance:    " + String.format("%.2f",getCurrentBalance(userName,accountDetails)) + " " + currency + ANSI_RESET);

        }
    }

    public void showHistory(String userName,MongoCollection<Document> transactions) {

        System.out.println(WHITE_UNDERLINED + ANSI_GREEN + "\nTransaction history" + ANSI_RESET);

        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                String jsonHistory = document.toJson();
                JsonNode jsonNode = null;

                try {
                    jsonNode = objectMapper.readTree(jsonHistory);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String split = "            ";
                String split1 = "                ";
                System.out.println(jsonNode.get("DATE").asText() + "\t" +
                                jsonNode.get("SERVICE").asText() + split.substring(jsonNode.get("SERVICE").asText().length())+
                                jsonNode.get("DESTINATION").asText()+ split1.substring(jsonNode.get("DESTINATION").asText().length())+
                                String.format("%.2f",Double.parseDouble(jsonNode.get("AMOUNT").asText())) + split.substring(String.format("%.2f",Double.parseDouble(jsonNode.get("AMOUNT").asText())).length())+
                                jsonNode.get("CURRENCY").asText());
            }
        };
        transactions.find(eq("userName", userName)).forEach(printBlock);
    }

    public void newPIN(String userName, String pin, MongoCollection<Document> credentials) throws NoSuchAlgorithmException, NoSuchProviderException, IOException {
        while(!check.isValidPin(pin))
        {
            System.out.println();
            System.out.print("PIN: ");
            pin = scanner.nextLine();
        }
        EncryptPass.setHash(pin, userName, credentials);
    }

    public void deleteAccount(String userName, MongoCollection<Document> credentials, MongoCollection<Document> transactions, MongoCollection<Document> accountDetails) {
        credentials.deleteOne(eq("userName", userName.toUpperCase()));
        accountDetails.deleteOne(eq("userName", userName.toUpperCase()));
        transactions.deleteMany(eq("userName", userName.toUpperCase()));
        }
}
