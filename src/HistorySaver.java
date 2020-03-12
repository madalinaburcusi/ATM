import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class HistorySaver {

    public void saveHistory( String userName, String date,
                             String service, String destination,
                             String amount, String currency) throws IOException {
        File file = new File("TransactionHistory.txt");

        if(!file.exists())
            file.createNewFile();

        String transaction = userName.toUpperCase() + "\t" +
                             date + "\t" +
                             service.toUpperCase() + "\t" +
                             destination.toUpperCase() + "\t" +
                             amount + "\t" + currency;

        Files.write(Paths.get("TransactionHistory.txt"), transaction.concat("\n").getBytes(), StandardOpenOption.APPEND);

    }

}
