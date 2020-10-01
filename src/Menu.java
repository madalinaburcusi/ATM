import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    final String ANSI_GREEN = "\u001B[32m";
    final String WHITE_UNDERLINED = "\033[4;37m";
    final String ANSI_RESET = "\u001B[0m";

    private AccountServices service = new AccountServices();
    private CheckInput check = new CheckInput();

    public void startMenu(String username, MongoCollection<Document> credentials, MongoCollection<Document> transactions, MongoCollection<Document> accountDetails) throws NoSuchAlgorithmException, NoSuchProviderException, IOException {
        String option, input;
        String backToMenuQuest = ANSI_GREEN + "\nBack to MENU? (Y/ any other key for No)" + ANSI_RESET;
        System.out.println(WHITE_UNDERLINED + ANSI_GREEN + "MENU" + ANSI_RESET);

        List<String> menu = new ArrayList<String>();
        menu.add("Current Balance      Code: 1");
        menu.add("Deposit Cash         Code: 2");
        menu.add("Withdraw Cash        Code: 3");
        menu.add("Pay Bills            Code: 4");
        menu.add("Transaction History  Code: 5");
        menu.add("Update PIN           Code: 6");
        menu.add("Delete Account       Code: 7");
        menu.add("Exit                 Code: 8");

        for (String row : menu) {
            System.out.println(row);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.print(ANSI_GREEN + "Please enter your option: " + ANSI_RESET);
        option = scanner.nextLine();

        while(!check.isValidMenuCode(option))
        {
            System.out.print(ANSI_GREEN + "Please enter your option: " + ANSI_RESET);
            option = scanner.nextLine();
        }

        //Implement menu
        String cancelTransaction = "Type " + ANSI_GREEN + WHITE_UNDERLINED + "CANCEL" + ANSI_RESET + " or " + ANSI_GREEN + WHITE_UNDERLINED + "0" + ANSI_RESET + " in order to return to MENU!";
        switch(Integer.parseInt(option))
        {
            case 1: {
                System.out.println(WHITE_UNDERLINED + ANSI_GREEN + "\nCurrent Balance" + ANSI_RESET);

                System.out.println(String.format("%.2f",service.getCurrentBalance(username,accountDetails)) + " " + service.currency);
                System.out.println(backToMenuQuest);
                input = scanner.nextLine();
                backToMenu(username,input,credentials,transactions,accountDetails);
                break;
            }

            case 2: {
                System.out.println();

                System.out.println(cancelTransaction);
                service.depositCash(username,transactions,accountDetails);

                System.out.println(backToMenuQuest);
                input = scanner.nextLine();
                backToMenu(username,input,credentials,transactions,accountDetails);
                break;
            }

            case 3: {
                System.out.println();
                System.out.println(cancelTransaction);
                service.withdrawCash(username,transactions,accountDetails);

                System.out.println(backToMenuQuest);
                input = scanner.nextLine();
                backToMenu(username,input,credentials,transactions,accountDetails);
                break;
            }

            case 4: {

                Provider provider = new Provider();
                provider.providerMenu(username,service,transactions,accountDetails);

                System.out.println(backToMenuQuest);
                input = scanner.nextLine();
                backToMenu(username,input,credentials,transactions,accountDetails);
                break;
            }

            case 5: {
                service.showHistory(username,transactions);

                System.out.println(backToMenuQuest);
                input = scanner.nextLine();
                backToMenu(username,input,credentials,transactions,accountDetails);

                break;
            }

            case 6: {
                System.out.print(ANSI_GREEN + "\nNew PIN: " + ANSI_RESET);
                input = scanner.nextLine();

                service.newPIN(username,input,credentials);
                System.out.println(ANSI_GREEN + "\nYour PIN has been updated.");
                System.out.println("Session ended." + ANSI_RESET);
                break;
            }
            case 7: {
                System.out.print("Are you sure that you want to delete this account? (Y/ any other key for No) ");
                input = scanner.nextLine();

                if(input.toUpperCase().equals("Y")) {
                    System.out.println();
                    service.deleteAccount(username,credentials,transactions,accountDetails);
                    System.out.println("Your account has been deleted!");
                    System.out.println("Session ended.");
                }else
                {
                    System.out.println();
                    backToMenu(username,"Y",credentials,transactions,accountDetails);
                }

                break;
            }

            case 8: {
                System.out.println("Thank you!");
                System.out.println("Session ended.");
                break;
            }
        }
    }

    public void backToMenu(String username, String input,MongoCollection<Document> credentials, MongoCollection<Document> transactions, MongoCollection<Document>  accountDetails) throws NoSuchAlgorithmException, NoSuchProviderException, IOException {

        if(input.toUpperCase().equals("Y")) {
            System.out.println();
            startMenu(username,credentials,transactions,accountDetails);
        }
        else {
            System.out.println("Thank you!");
            System.out.println("Session ended");
        }
    }


}
