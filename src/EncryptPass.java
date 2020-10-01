import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class EncryptPass {


    public static String getHash(String PIN) throws NoSuchProviderException, NoSuchAlgorithmException, IOException {
        Security.addProvider(new BouncyCastleProvider());
        byte[] hash = null;

        MessageDigest md_SHA = MessageDigest.getInstance("SHA-1","BC");
        MessageDigest md_md5 = MessageDigest.getInstance("MD5","BC");

        md_SHA.update(PIN.getBytes());
        hash = md_md5.digest(DatatypeConverter.printHexBinary(md_SHA.digest()).getBytes());

        return DatatypeConverter.printHexBinary(hash);
    }

}
