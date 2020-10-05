import com.mongodb.client.MongoCollection;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bson.Document;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.mindrot.jbcrypt.BCrypt;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import static com.mongodb.client.model.Filters.eq;
public class EncryptPass {

    public static String getSalt(String userName, MongoCollection credentials) throws IOException { // from dataBase
        Document myDoc = (Document) credentials.find(eq("userName", userName.toUpperCase())).first();
        String jsonResult = myDoc.toJson();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResult);
        return jsonNode.get("SALT").asText();
    }

    public static void setSalt(String userName, MongoCollection credentials){
        credentials.updateOne(eq("userName", userName.toUpperCase()), new Document("$set", new Document("userName", userName.toUpperCase()).append("SALT", BCrypt.gensalt(10))));
    }

    public static void setHash(String PIN, String userName, MongoCollection credentials) throws NoSuchProviderException, NoSuchAlgorithmException, IOException {
        setSalt(userName, credentials);
        credentials.updateOne(eq("userName", userName.toUpperCase()), new Document("$set", new Document("userName", userName.toUpperCase()).append("PIN", getHash(PIN, userName, credentials))));
    }

    public static String getHash(String PIN, String userName, MongoCollection credentials) throws NoSuchProviderException, NoSuchAlgorithmException, IOException {
        Security.addProvider(new BouncyCastleProvider());
        byte[] hash;

        MessageDigest md_SHA = MessageDigest.getInstance("SHA-1","BC");
        MessageDigest md_md5 = MessageDigest.getInstance("MD5","BC");

        md_SHA.update((getSalt(userName,credentials)+PIN).getBytes());
        hash = md_md5.digest(DatatypeConverter.printHexBinary(md_SHA.digest()).getBytes());

        return DatatypeConverter.printHexBinary(hash);
    }

}
