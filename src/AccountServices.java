import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Scanner;

public class AccountServices {

    public static final String WHITE_UNDERLINED = "\033[4;37m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    final String currency = "RON";

    String service,destination;
    private CheckInput check = new CheckInput();
    private HistorySaver saveToFile = new HistorySaver();
    private Scanner scanner = new Scanner(System.in);

    LocalDate date;

    public String register(String outfile) throws IOException {
        String IBAN, PIN, userName;

        System.out.println();
        System.out.print("User Name: ");
        userName =  scanner.nextLine().toUpperCase();

        while(check.userNameExists(userName,outfile))
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

        Files.write(Paths.get(outfile), (userName+"\t"+ IBAN+"\t"+ PIN).concat("\n").getBytes(), StandardOpenOption.APPEND);

        System.out.println();
        System.out.println(ANSI_GREEN + "Your account has been created." + ANSI_RESET);
        System.out.println();
        return userName;
    }

    public double getCurrentBalance(String userName) throws IOException {
        double currentBalance = 0;
        File file = new File("TransactionHistory.txt");
        FileReader f = new FileReader(file);
        BufferedReader b = new BufferedReader(f);

        String line;
        while((line = b.readLine()) != null) {
            String[] columns = line.split("\t");

            if(columns[0].toUpperCase().equals(userName.toUpperCase()) )
            {
                switch (columns[2])
                {
                    case "WITHDRAW" : {
                        currentBalance -= Double.parseDouble(columns[4]);
                        break;
                    }

                    case "DEPOSIT" : {
                        currentBalance += Double.parseDouble(columns[4]);
                        break;
                    }

                    case "BILLING" : {
                        currentBalance -= Double.parseDouble(columns[4]);
                        break;
                    }
                }
            }

        }
        f.close();

        return currentBalance;
    }

    public boolean depositCash(String userName, String cash) throws IOException {
        if(!check.isValidLongInput(cash))
        {
            return false;
        }
        else {
            System.out.println();
            System.out.println("You have deposited " + cash + " " + currency + " in the current account.");

            destination = "debit account";
            service = "deposit";
            saveToFile.saveHistory(userName,date.now().toString(), service, destination, cash, currency);
            return true;
        }
    }

    public boolean withdrawCash(String userName,String cash) throws IOException {
        if(!check.isValidLongInput(cash))
        {
            return false;
        }
        else {
            if(Long.parseLong(cash) > getCurrentBalance(userName.toUpperCase()))
            {
                System.out.println("Insufficient founds.");
                return false;
            }
            else{
                System.out.println();
                System.out.println("You have withdrawn " + cash + " " + currency + " from the current account.");

                destination = "cash";
                service = "withdraw";
                saveToFile.saveHistory(userName,date.now().toString(), service, destination,cash, currency);

                return true;
            }
        }

    }

    public void payBills(String userName,double amount, Provider provider) throws IOException {
        if(amount > getCurrentBalance(userName))
            System.out.println("Insufficient founds.");
        else{
            System.out.println();
            System.out.println("You have payed " + amount + " " + currency + " to " + provider.provider + ".");

            service = "Billing";
            destination =  provider.provider;
            saveToFile.saveHistory(userName,date.now().toString(), service, destination,String.valueOf(amount), currency);

            System.out.println(ANSI_GREEN + "Current Balance:    " + getCurrentBalance(userName) + " " + currency + ANSI_RESET);

        }
    }

    public void showHistory(String userName) throws IOException {
        File file = new File("TransactionHistory.txt");
        FileReader f = new FileReader(file);
        BufferedReader b = new BufferedReader(f);

        System.out.println(WHITE_UNDERLINED + ANSI_GREEN + "\nTransaction history" + ANSI_RESET);

        String line;
        while ((line = b.readLine()) != null) {
            String[] columns = line.split("\t");

            if (columns[0].toUpperCase().equals(userName.toUpperCase())) {
                System.out.println(columns[1] + "\t" + columns[2] + "\t" + columns[3]+ "\t" + columns[4] + "\t" + columns[5] );
            }
        }
    }

    public void newPIN(String userName, String pin, String logFile)throws IOException{
        File tempFile = new File("tempFileUpdate.txt");
        File logInFile = new File(logFile);
        BufferedReader reader = new BufferedReader(new FileReader(logFile));
        FileWriter writer = new FileWriter("tempFileUpdate.txt");

        while(!check.isValidPin(pin))
        {
            System.out.println();
            System.out.print("PIN: ");
            pin = scanner.nextLine();
        }

        StringBuffer sb=new StringBuffer("");
        String line;
        while((line = reader.readLine()) != null) {
            String[] columns = line.split("\t");

            if(!columns[0].toUpperCase().equals(userName.toUpperCase()) )
            {
                sb.append(line+"\n");
            }else {
                sb.append(line.replace(columns[2],pin));
                sb.append("\n");
            }
        }

        writer.write(sb.toString());
        writer.close();
        reader.close();

        logInFile.delete();
        tempFile.renameTo(logInFile);
    }

    public void deleteAccount(String userName, String logFile) throws IOException {
        String[] files = {logFile,"TransactionHistory.txt"};

        for(String file: files){
            File tempFile = new File("tempFile.txt");
            File logInFile = new File(file);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            FileWriter writer = new FileWriter("tempFile.txt");

            StringBuffer sb=new StringBuffer("");
            String line;

            while((line = reader.readLine()) != null) {
                String[] columns = line.split("\t");
                if (!columns[0].toUpperCase().equals(userName.toUpperCase()))
                    sb.append(line+"\n");
            }

            writer.write(sb.toString());
            writer.close();
            reader.close();

            logInFile.delete();
            tempFile.renameTo(logInFile);
        }
    }

}
