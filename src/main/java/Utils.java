import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {

    // this method reads a line from console
    public static String readFromConsole() {
        String optionToExecute = "";

        try {
            InputStreamReader readFromConsole = new InputStreamReader(System.in);
            BufferedReader bufferedReadFromConsole = new BufferedReader(readFromConsole);

            optionToExecute = bufferedReadFromConsole.readLine();
            return optionToExecute;
        } catch(IOException e) {
            System.out.println(e);
        }
        return optionToExecute;
    }
}
