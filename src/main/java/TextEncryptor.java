import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TextEncryptor {

    private char[] key;
    private static TextEncryptor instance;
    Path passwordsLocation;

    public static TextEncryptor getInstance() {
        if(instance == null) {
            instance = new TextEncryptor();
        }
        return instance;
    }

    private TextEncryptor() {}

    // a method to prepare text, send the file to encryption and save encrypted text
    public void encrypt() {

        // checking if the file exists
        Path filePath = Utils.getOriginalPasswordsLocation();
        if(filePath.toFile().exists()) {
            System.out.println("File found.");
        } else {
            System.out.println("File not found. Check if the file path is correct.");
            return;
        }

        // setting path where encrypted passwords will be placed
        Path encodeTo = Utils.getEncryptedPasswordsLocation();
        passwordsLocation = encodeTo;

        // entering the key
        System.out.println("Enter the encryption key.");
        this.key = Utils.readFromConsole().toCharArray();
        System.out.println("Key accepted.");
        System.out.println("Encryption procedure started, please wait...");

        /* reading file contents and transforming into UTF-8 in case if it has
        different encoding */
        String fileContent = "";
        try {
            fileContent = Files.readString(filePath, StandardCharsets.UTF_8);
        } catch(IOException e) {
            e.printStackTrace();
        }

        // sending the content for encryption and receiving the encrypted content
        String encryptedContent = encryptingProcedure(fileContent);

        //writing encrypted content to a file
        try {
            if(Files.exists(encodeTo)) {
                Files.writeString(encodeTo,
                        encryptedContent,
                        StandardCharsets.UTF_8,
                        StandardOpenOption.TRUNCATE_EXISTING,
                        StandardOpenOption.WRITE);
            } else {
                Files.writeString(encodeTo,
                        encryptedContent,
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE_NEW,
                        StandardOpenOption.WRITE);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        System.out.println("Encryption completed. \n");
    }


    /* encryption procedure carcass. this method chooses option to execute
    depending on the length of the encrypted content and the encryption key */
    private String encryptingProcedure(String stringFileContent) {

        char[] originalContent = stringFileContent.toCharArray();
        char[] encryptedContent;

        if((originalContent.length % key.length) != 0) {

            /* if the text length is not a multiple of the key length,
            then add the required number of dummy characters at the end of
            the array and a the end of string sign which, being
            converted to an integer, tells how many
            dummy characters were put
            */

            // making content multiple to key length by adding dummy characters
            char[] preparedContent = addDummies(originalContent);

            // encrypting prepared content
            encryptedContent = blockEncrypting(preparedContent);

        } else {
            /* if the content of the file is a multiple of the key length,
            then we encrypt the file and add an end of string sign
            at the end as an extra character */

            // encrypting the content
            encryptedContent = blockEncrypting(originalContent);

            // adding the end of string sign
            encryptedContent = addEndOfStringSign(encryptedContent);
        }
        return new String(encryptedContent);
    }


    /* we use this method if the length of encrypting text is not multiple to
    key length. The method adds required number of dummy characters at the end of string
    to make it multiple to the key length. At the end of string we put
    the end of string sign - a dummy character that, being converted to integer,
    tells how many dummy characters were put.
     */
    private char[] addDummies(char[] originalContent) {

        int keyLength = key.length;
        int originalLength = originalContent.length; // the original text length

        /* this variable contains the number of dummy characters to add at the
        end of the array to make it multiple to key length
        */
        int leftToFill = (keyLength - (originalLength % keyLength));

        //creating an empty array from the original + number of
        // dummies left to add
        char[] addedOriginalContent = new char[originalContent.length + leftToFill];

        // fill the new array with the contents of the original array
        for(int index = 0; index < originalLength; index++) {
            addedOriginalContent[index] = originalContent[index];
        }

        // putting to the array the end of string sign that contains
        // information of number of added dummies
        char dummiesAmount = (char) leftToFill;
        addedOriginalContent[addedOriginalContent.length - 1] = dummiesAmount;

        // calculate from the element with what index
        // the array should be filled with dummies
        int dummyStartIndex = addedOriginalContent.length - leftToFill;
        // filling the end of new array with dummies, except the
        // end of sting sing (leftToFill -2)
        for(int index = 0; index < (leftToFill - 2); index++) {
            // dummies are space symbols
            addedOriginalContent[dummyStartIndex + index] = ' ';
            // adding the end of string sign that contains a number of dummies
            addedOriginalContent[addedOriginalContent.length - 1] = (char) leftToFill;
        }
        return addedOriginalContent;
    }

    // a method for encrypting prepared text
    private char[] blockEncrypting(char[] preparedContent) {

        char[] tempStorage = new char[key.length]; // temporal storage for the part of text being encrypted
        char[] encryptedContent = new char[preparedContent.length]; // storage for the encrypted content

        // add a block of text with a key
        for(int blockIndex = 0; blockIndex < preparedContent.length; blockIndex += key.length) {

            // filling temporal storage with the block of original content
            for(int storageIndex = 0; storageIndex < tempStorage.length; storageIndex++) // ???????? ???????????????? - 1 ???????????? ?????????????????? ?????????????????? ????????????
            {
                tempStorage[storageIndex] = preparedContent[blockIndex+storageIndex];
            }

            // adding temporal storage and the key and putting the result to the final array
            for(int sumIndex = 0; sumIndex < key.length; sumIndex++) {
                encryptedContent[blockIndex + sumIndex] = (char) (tempStorage[sumIndex] + key[sumIndex]);
            }
        }

        return encryptedContent;
    }

    // a method to end of string sign
    private char[] addEndOfStringSign(char[] encryptedContent) {

        // augmented array. the last index is for the end of string sign
        char[] addedEncryptedContent =  new char[encryptedContent.length + 1];

        // transfer encrypted content to the augmented array
        for(int index = 0; index < encryptedContent.length; index++) {
            addedEncryptedContent[index] = encryptedContent[index];
        }

        // putting the end of string sign and also encrypt it
        addedEncryptedContent[addedEncryptedContent.length - 1] = (char) ('*' + key[0]);

        return addedEncryptedContent;
    }

    public void setKey(String key) {
        this.key = key.toCharArray();
    }

    public Path getPasswordsLocation() {
        return passwordsLocation;
    }

    public void setPasswordsLocation(Path passwordsLocation) {
        this.passwordsLocation = passwordsLocation;
    }
}
