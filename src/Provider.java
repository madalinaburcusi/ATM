import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Provider {

    final String ANSI_GREEN = "\u001B[32m";
    final String WHITE_UNDERLINED = "\033[4;37m";
    final String ANSI_RESET = "\u001B[0m";

    String provider;
    String IBAN;

    Provider(){}

    Provider(String IBAN, String provider){
        this.IBAN = IBAN;
        this.provider = provider;
    }

    String input;
    Scanner scanner = new Scanner(System.in);
    CheckInput check = new CheckInput();

    public void providerMenu (String userName, AccountServices account, MongoCollection<Document> transactions, MongoCollection<Document> accountDetails) {
        List<Provider> providers = new ArrayList<Provider>();
        System.out.println(WHITE_UNDERLINED + ANSI_GREEN + "\nProviders:" + ANSI_RESET);
        providers.add(new Provider("076","Telecom"));
        providers.add(new Provider("000","ENEL"));
        providers.add(new Provider("111","APANOVA"));

        int i = 1;
        for (Provider provider : providers) {
            System.out.println(provider.provider + "\t\t\tCode: " + i);
            i++;
        }

        System.out.println();
        System.out.print(ANSI_GREEN + "Choose Provider: " + ANSI_RESET);
        input = scanner.nextLine();
        if(input.equals("0") || input.equalsIgnoreCase("cancel"))
            System.out.println("Operation canceled.");
        else{
            while (!check.isValidProviderCode(input)) {
                System.out.print(ANSI_GREEN + "Choose Provider: " + ANSI_RESET);
                input = scanner.nextLine();
            }
            int provider = Integer.parseInt(input);

            switch (provider) {
                case 1: {
                    double amount = Double.parseDouble(getValidAmount());
                    account.payBills(userName, amount, providers.get(0), transactions, accountDetails);
                    break;
                }
                case 2: {
                    double amount = Double.parseDouble(getValidAmount());
                    account.payBills(userName, amount, providers.get(1), transactions, accountDetails);
                    break;
                }
                case 3: {
                    double amount = Double.parseDouble(getValidAmount());
                    account.payBills(userName, amount, providers.get(2), transactions, accountDetails);
                    break;
                }
            }
        }
    }

    public String getValidAmount(){
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.print(ANSI_GREEN + "Amount:      " + ANSI_RESET);
        input = scanner.nextLine();

        while(!check.isValidDoubleInput(input)){
            System.out.print(ANSI_GREEN + "Amount:      " + ANSI_RESET);
            input = scanner.nextLine();
        }
        return input;
    }

}
