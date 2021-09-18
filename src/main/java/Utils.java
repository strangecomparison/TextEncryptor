import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class Utils {

    // application global variable that contains path to the file with passwords
    private static Path originalPasswordsLocation = null;
    // application global variable that contains path to the file with encrypted passwords
    private static Path encryptedPasswordsLocation = null;

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

    public static void setOriginalPasswordsLocation(Path path) {
        originalPasswordsLocation = path;
    }

    public static Path getOriginalPasswordsLocation() {
        return originalPasswordsLocation;
    }

    public static Path getEncryptedPasswordsLocation() {
        return encryptedPasswordsLocation;
    }

    public static void setEncryptedPasswordsLocation(Path encryptedPasswordsLocation) {
        Utils.encryptedPasswordsLocation = encryptedPasswordsLocation;
    }
}
