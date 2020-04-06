import org.junit.Test;
import static org.junit.Assert.*;

public class CheckInputTest {


    @Test
    public void isValidIBAN_shouldReturnFalseIfIBANNotMatchPattern() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidIBAN("RO 90 UGBI 0000 4820 0768 8 .");
        assertFalse(result);
    }

    @Test
    public void isValidIBAN_shouldReturnTrueIfIBANMatchesPattern() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidIBAN("RO 90 UGBI 0000 4820 0768 8 RON");
        assertTrue(result);
    }

    @Test
    public void isValidPin_shouldReturnFalseIfPINNot4Digits() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidPin("12369");
        assertFalse(result);
    }

    @Test
    public void isValidPin_shouldReturnFalseIfPINNotAllDigits() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidPin("123s");
        assertFalse(result);
    }

    @Test
    public void isValidPin_shouldReturnTrueIfPIN4Digits() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidPin("1236");
        assertTrue(result);
    }

    @Test
    public void isValidLoginCode_shouldBeTrueIfLoginCode_1to3() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidLoginCode("1");
        assertTrue(result);
    }

    @Test
    public void isValidLoginCode_shouldBeFalseIfLoginCodeNot_1to3() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidLoginCode("4");
        assertFalse(result);
    }

    @Test
    public void isValidLoginCode_shouldBeFalseIfLoginCodeNotInt() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidLoginCode("f");
        assertFalse(result);
    }

    @Test
    public void isValidMenuCode_shouldReturnTrueIfCode_1to8() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidMenuCode("1");
        assertTrue(result);
    }

    @Test
    public void isValidMenuCode_shouldReturnFalseIfCodeNot_1to8() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidMenuCode("9");
        assertFalse(result);
    }

    @Test
    public  void isValidMenuCode_shouldReturnFalseIfCodeNotDigit() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidMenuCode("d");
        assertFalse(result);
    }

    @Test
    public void isValidLongInput_shouldReturnFalseIfLessThan0() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidLongInput("-1");
        assertFalse(result);
    }

    @Test
    public void isValidLongInput_shouldReturnFalseIfNotLong() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidLongInput("6.3");
        assertFalse(result);
    }

    @Test
    public void isValidLongInput_shouldReturnTrueIfGraterOrEqualTo0() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidLongInput("1");
        assertTrue(result);
    }

    @Test
    public void isValidDoubleInput_shouldReturnFalseIfLessThan0() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidDoubleInput("-1.5");
        assertFalse(result);
    }

    @Test
    public void isValidDoubleInput_shouldReturnFalseIfNotDouble() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidDoubleInput("a");
        assertFalse(result);
    }

    @Test
    public void isValidDoubleInput_shouldReturnTrueIfGraterOrEqualTo0() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidDoubleInput("1");
        assertTrue(result);
    }

    @Test
    public void isValidProviderCode_shouldReturnTrueIfCode_1to3() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidProviderCode("1");
        assertTrue(result);
    }

    @Test
    public void isValidProviderCode_shouldReturnFalseIfCodeNot_1to3() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidProviderCode("4");
        assertFalse(result);
    }

    @Test
    public void isValidProviderCode_shouldReturnFalseIfCodeNotDigit() {
        CheckInput check = new CheckInput();
        boolean result = check.isValidProviderCode("d");
        assertFalse(result);
    }

}