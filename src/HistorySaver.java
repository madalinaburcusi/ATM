import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class HistorySaver {

    public void saveHistory(String userName, String date,
                            String service, String destination,
                            String amount, String currency,
                            MongoCollection<Document> transactions) {

        Document doc = new Document("userName",userName.toUpperCase())
                .append("DATE", date)
                .append("SERVICE", service.toUpperCase())
                .append("DESTINATION", destination.toUpperCase())
                .append("AMOUNT",amount)
                .append("CURRENCY",currency);
        transactions.insertOne(doc);

    }

}
