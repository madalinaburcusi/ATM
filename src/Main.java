import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_RESET = "\u001B[0m";
    static final String RED_BOLD = "\033[1;31m";

    static CheckInput check = new CheckInput();
    static LogIn log = new LogIn();
    static Scanner scanner = new Scanner(System.in);
    static AccountServices accountServices = new AccountServices();

    public static void main(String[] args) throws IOException {

        final String logInFile = "logInFile.txt";
        String userName, PIN;
        String option;
        int numberOfLoginTrials = 1;

        System.out.println(ANSI_GREEN + "WELCOME!"+ ANSI_RESET);
        System.out.println();

        System.out.print("User Name: ");
        userName = scanner.nextLine();
        System.out.print("PIN: ");
        PIN = scanner.nextLine();

        if(log.logIn(userName,PIN,logInFile) == false)
        {
            List<String> menu = new ArrayList<String>();
            menu.add(ANSI_GREEN +   "Retry         Code: 1");
            menu.add(               "New Account   Code: 2");
            menu.add(               "Exit          Code: 3" + ANSI_RESET);

            for (String row : menu) {
                System.out.println(row);
            }

            System.out.println();
            System.out.print(ANSI_GREEN + "Enter your option: " + ANSI_RESET);
            option = scanner.nextLine();

            while(!check.isValidLoginCode(option))
            {
                System.out.print(ANSI_GREEN + "Enter your option: " + ANSI_RESET);
                option = scanner.nextLine();
            }

            switch (option)
            {
                case "1" : {
                    System.out.println();

                    while(log.logIn(userName,PIN,logInFile)== false && numberOfLoginTrials<3)
                    {
                        System.out.print(ANSI_GREEN +   "User Name: ");
                        userName = scanner.nextLine();
                        System.out.print(               "PIN: " + ANSI_RESET);
                        PIN = scanner.nextLine();
                        numberOfLoginTrials +=1;
                    }

                    if(numberOfLoginTrials>=3)
                    {
                        System.out.println(RED_BOLD + "\nYou have exceeded maximum times of login." + ANSI_RESET);
                        System.out.println(ANSI_GREEN + "Session ended." + ANSI_RESET);
                        System.exit(0);
                    }

                    break;
                }

                case "2" : {
                    userName = accountServices.register(logInFile);
                    break;
                }

                case "3" : {
                    System.out.println("Thank you!");
                    System.out.println("Session ended");
                    break;
                }

            }

        }

        //Menu
        Menu menu = new Menu();
        menu.startMenu(userName, logInFile);

    }

}
