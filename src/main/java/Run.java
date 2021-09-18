import java.nio.file.Path;

public class Run {

    public static void main(String[] args) throws InterruptedException {

        // setting application global variable containing location of file with passwords
        Utils.setOriginalPasswordsLocation(Path.of(args[0]));
        Utils.setEncryptedPasswordsLocation(Path.of(args[1]));

        System.out.print("Welcome to the text encryptor. ");

        while(true) {

            System.out.println("Choose the option: \n");
            System.out.println("1. Encrypt passwords");
            System.out.println("2. Watch passwords");
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
