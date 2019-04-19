import controller.HashController;

import java.io.File;
import java.util.Map;

/**
 * The main class which contains the main method
 */
public class Main {

    /**
     * The main method
     *
     * @param args the filename
     * @throws Exception if there is no argument (i.e. no file)
     */
    public static void main(String[] args) throws Exception {

        String[] hashes = {"MD5", "SHA-256", "SHA-512"};
        String filename;

        if (args.length == 1) {
            filename = args[0];
        } else {
            throw new Exception("No File Specified");
        }

        HashController hashController = new HashController(hashes, new File(filename));
        Map<String, String> result = hashController.runAndGetHashes();

        for (String hash : hashes) {
            System.out.println(hash + " = " + result.get(hash));
        }

    }
}
