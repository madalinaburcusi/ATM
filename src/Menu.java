import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    final String ANSI_GREEN = "\u001B[32m";
    final String WHITE_UNDERLINED = "\033[4;37m";
    final String ANSI_RESET = "\u001B[0m";

    private Services service = new Services();
    private CheckInput check = new CheckInput();

    public void startMenu(String username, String loginFile) throws IOException {
        String option, input;
        double currentBalance;

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
        switch(Integer.parseInt(option))
        {
            case 1: {
                System.out.println(WHITE_UNDERLINED + ANSI_GREEN + "\nCurrent Balance" + ANSI_RESET);

                System.out.println(service.getCurrentBalance(username) + " " + service.currency);
                System.out.println(ANSI_GREEN + "\nBack to MENU? (Y/N)" + ANSI_RESET);
                input = scanner.nextLine();
                backToMenu(username,input,loginFile);
                break;
            }

            case 2: {
                System.out.println();
                System.out.print(ANSI_GREEN + "Amount: " + ANSI_RESET);
                input = scanner.nextLine();

                while(!service.depositCash(username,input)){
                    System.out.print(ANSI_GREEN + "Amount: " + ANSI_RESET);
                    input = scanner.nextLine();
                }

                System.out.println(ANSI_GREEN + "\nBack to MENU? (Y/N)" + ANSI_RESET);
                input = scanner.nextLine();
                backToMenu(username,input,loginFile);
                break;
            }

            case 3: {
                System.out.println();
                System.out.print(ANSI_GREEN + "Amount: " + ANSI_RESET);
                input = scanner.nextLine();

                while(!service.withdrawCash(username,input)){
                    System.out.print(ANSI_GREEN + "Amount: " + ANSI_RESET);
                    input = scanner.nextLine();
                }

                System.out.println(ANSI_GREEN + "\nBack to MENU? (Y/N)" + ANSI_RESET);
                input = scanner.nextLine();
                backToMenu(username,input,loginFile);
                break;
            }

            case 4: {

                Provider provider = new Provider();
                provider.providerMenu(username,service);

                System.out.println(ANSI_GREEN + "\nBack to MENU? (Y/N)" + ANSI_RESET);
                input = scanner.nextLine();
                backToMenu(username,input,loginFile);
                break;
            }

            case 5: {
                service.showHistory(username);

                System.out.println(ANSI_GREEN + "\nBack to MENU? (Y/N)" + ANSI_RESET);
                input = scanner.nextLine();
                backToMenu(username,input,loginFile);

                break;
            }

            case 6: {
                System.out.print(ANSI_GREEN + "\nNew PIN: " + ANSI_RESET);
                input = scanner.nextLine();

                service.newPIN(username,input,loginFile);
                System.out.println(ANSI_GREEN + "\nYour PIN has been updated.");
                System.out.println("Session ended." + ANSI_RESET);
                break;
            }
            case 7: {
                System.out.print("Are you sure that you want to delete this account? (Y/N) ");
                input = scanner.nextLine();

                if(input.toUpperCase().equals("Y")) {
                    System.out.println();
                    service.deleteAccount(username,loginFile);
                    System.out.println("Your account has been deleted!");
                    System.out.println("Session ended");
                }else
                {
                    System.out.println();
                    backToMenu(username,"Y",loginFile);
                }

                break;
            }

            case 8: {
                System.out.println("Thank you!");
                System.out.println("Session ended");
                break;
            }
        }
    }

    public void backToMenu(String username, String input, String logInFile) throws IOException {

        if(input.toUpperCase().equals("Y")) {
            System.out.println();
            startMenu(username,logInFile);
        }
        else {
            System.out.println("Thank you!");
            System.out.println("Session ended");
        }
    }


}
