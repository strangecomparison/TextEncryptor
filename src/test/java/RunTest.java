import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.params.ParameterizedTest;
import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class RunTest {

    @Nested
    @DisplayName("Reading from console")
    class ReadFromConsoleTest {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, Integer.MIN_VALUE, Integer.MAX_VALUE})
        @DisplayName("Reading ints info from console")
        void readFromConsole_Ints(int args) throws IOException {
            // подменяем стандартный поток ввода
            System.setIn(new ByteArrayInputStream(Integer.
                    valueOf(args).
                    toString().
                    getBytes(StandardCharsets.UTF_8)));

            String result = Utils.readFromConsole();
            String expected = Integer.valueOf(args).toString();

            assertEquals(expected, result);
            // возвращаем стандартный поток ввода
            System.setIn(System.in);
        }

        @ParameterizedTest
        @ValueSource(strings = {"Hello", "Ваня", "123", "oooOOO"})
        @DisplayName("Reading strings info from console")
        void readFromConsole_Strings(String args) throws IOException {
            // подменяем стандартный поток ввода
            System.setIn(new ByteArrayInputStream(args.getBytes(StandardCharsets.UTF_8)));

            String result = Utils.readFromConsole();
            String expected = args;

            assertEquals(expected, result);
            // возвращаем стандартный поток ввода
            System.setIn(System.in);
        }

    }
}
