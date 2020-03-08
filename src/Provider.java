import java.io.IOException;
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

    public void providerMenu (String userName, Services account) throws IOException {
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
        while(!check.isValidProviderCode(input)){
            System.out.print(ANSI_GREEN + "Choose Provider: " + ANSI_RESET);
            input = scanner.nextLine();
        }
        int provider = Integer.parseInt(input);

        switch(provider){
            case 1 : {
                double amount = Double.parseDouble(check.validAmount());
                account.payBills(userName,amount,providers.get(0));
                break;
            }
            case 2 : {
                double amount = Double.parseDouble(check.validAmount());
                account.payBills(userName,amount,providers.get(1));
                break;
            }
            case 3 : {
                double amount = Double.parseDouble(check.validAmount());
                account.payBills(userName,amount,providers.get(2));
                break;
            }
        }
    }
}
