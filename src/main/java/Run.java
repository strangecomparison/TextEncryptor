import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Run {

    public static void main(String[] args) throws InterruptedException {

        System.out.print("Welcome to the text encryptor. ");

        while(true) {

            System.out.println("Choose the option: \n");
            System.out.println("1. Encrypt the file");
            System.out.println("2. Decrypt the file");
            System.out.println("3. Quit Text Encryptor");

            String optionToExecute = Utils.readFromConsole();

            // run encryptor or decryptor depending on chose option
            if (optionToExecute.equals("1")) {
                TextEncryptor textEncryptor = TextEncryptor.getInstance();
                textEncryptor.encrypt();
            } else if (optionToExecute.equals("2")) {
                TextDecryptor textDecryptor = TextDecryptor.getInstance();
                textDecryptor.decrypt();
            } else if(optionToExecute.equals("3")) {
                System.out.println("Quitting Text Encryptor...");
                System.exit(0);
            }
            else {
                System.out.println("You have chosen non-existing option. Try again.");
            }
        }
    }
}
