import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextEncryptorTest {

    TextEncryptor textEncryptor;

    @BeforeEach
    void init() {
        textEncryptor = TextEncryptor.getInstance();
    }


    @Test
    @DisplayName("Testing encrypting procedure")
    void encryptingProcedureTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String contentToEncrypt = "Hello, world!";
        // получаем доступ к private методу с использованием Reflection API
        Method method = TextEncryptor.class.getDeclaredMethod("encryptingProcedure", String.class);
        method.setAccessible(true);

        textEncryptor.setKey("sobaka");
        String result = (String) method.invoke(textEncryptor, contentToEncrypt);
        String expected = "»ÔÎÍÚ\u008D\u0093æÑÓ×Å\u0094\u008F\u0082\u0081kf";
        System.out.println(result);
        assertEquals(expected, result);
    }

}
