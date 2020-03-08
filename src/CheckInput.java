import java.io.*;
import java.util.Scanner;

public class CheckInput {
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_RESET = "\u001B[0m";
    final String RED_BOLD = "\033[1;31m";
    int count = 0;

    public boolean userNameExists(String user, String outfile) throws IOException {
        FileReader f = new FileReader(outfile);
        BufferedReader b = new BufferedReader(f);
        boolean userExists = false;

        //Check if the username already exists
        String line;
        while((line = b.readLine()) != null) {
            String[] columns = line.split("\t");

            if(columns[0].equals(user.toUpperCase()) )
            {
                System.out.println(RED_BOLD + "\nUser name already exists." + ANSI_RESET);
                userExists =  true;
                break;
            }else continue;

        }

        f.close();

        return userExists;
    }

    public boolean isUserPinValid(String user,String pin, String outfile) throws IOException {
        FileReader f = new FileReader(outfile);
        BufferedReader b = new BufferedReader(f);

        int userPinValid = 0;
        //Check if the username already exists
        String line;
        while((line = b.readLine()) != null) {
            String[] columns = line.split("\t");

            if(columns[0].equals(user.toUpperCase()) )
            {
                userPinValid +=1;
            }
        }

        f.close();
        FileReader f1 = new FileReader(outfile);
        BufferedReader b1 = new BufferedReader(f1);

        while((line = b1.readLine()) != null) {
            String[] columns = line.split("\t");
            if (pin.equals(columns[2]))
                userPinValid +=1;

        }

        f1.close();

        if (userPinValid == 2) {
            return true;
        }else {
            count++;
            if(count!=2)
                System.out.println(RED_BOLD + "User or PIN does not exist.\n" + ANSI_RESET);
            return false;
        }

    }

    public boolean isValidIBAN(String cardNumber) {
            if (cardNumber.length() == 24) {
                return true;
            }
         else{
            System.out.println(RED_BOLD + "Not a valid Card Number." + ANSI_RESET);
            return false;
        }
    }

    public boolean isValidPin(String pin){
        try {
            int PIN = Integer.parseInt(pin);
                if (pin.length() != 4) {
                    System.out.println(RED_BOLD + "Not a valid PIN. 4 numerical digits required." + ANSI_RESET);
                    return false;
                }
                    return true;
            } catch (Exception e) {
                System.out.println(RED_BOLD + "Not a valid PIN. 4 numerical digits required." + ANSI_RESET);
                return false;
            }
    }

    public boolean isValidLogInCode(String option){
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
            if(code <1 || code >4)
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

    public String validAmount(){
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.print(ANSI_GREEN + "Amount:      " + ANSI_RESET);
        input = scanner.nextLine();

        while(!isValidDoubleInput(input)){
            System.out.print(ANSI_GREEN + "Amount:      " + ANSI_RESET);
            input = scanner.nextLine();
        }
        return input;
    }


}
