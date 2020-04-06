import org.junit.Test;
import java.io.ByteArrayInputStream;
import static org.junit.Assert.*;

public class AccountServicesTest {

    @Test
    public void getValidCash_shouldReturn3asCash() {
        AccountServices services = new AccountServices();
        String input = "d\n"
                    + "6.8\n"
                    + "4\n"
                    + "dfg\n"
                    + "3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        assertEquals("4", services.getValidCash());
    }


}