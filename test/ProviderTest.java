import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

public class ProviderTest {

    @Test
    public void getValidAmount_shouldReturn3asAmount() {
        Provider provider = new Provider();
        String input = "d\n"
                + "Mother\n"
                + "3\n"
                + "dfg\n"
                + "4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        assertEquals("3", provider.getValidAmount());
    }

}