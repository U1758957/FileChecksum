package hash;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class performs a hash and outputs a checksum
 */
public class Hash implements Runnable {

    public Thread t;
    private String algorithm;
    private File file;
    private String hashResult;

    /**
     * The constructor, initiates the thread and the parameters
     *
     * @param algorithm the hashing algorithm to use (between MD5, SHA-256, and SHA-512)
     * @param file      the file to perform the hashing on
     */
    public Hash(String algorithm, File file) {
        this.t = new Thread(this);
        this.algorithm = algorithm;
        this.file = file;
    }

    /**
     * Hash a file and output its checksum
     *
     * @throws IOException              if the file cannot be read
     * @throws NoSuchAlgorithmException if the algorithm does not exist
     */
    private void hashFile() throws IOException, NoSuchAlgorithmException {

        StringBuilder hex = new StringBuilder();

        InputStream input = new FileInputStream(file);
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        byte[] buffer = new byte[2048];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            digest.update(buffer, 0, bytesRead);
        }
        byte[] hashedBytes = digest.digest();
        for (byte x : hashedBytes) hex.append(String.format("%1$02X", x));
        setHashResult(hex.toString());

    }

    /**
     * Get the checksum
     *
     * @return the hash
     */
    public String getHashResult() {
        return hashResult;
    }

    /**
     * Set the checksum
     *
     * @param hashResult the hash
     */
    private void setHashResult(String hashResult) {
        this.hashResult = hashResult;
    }

    @Override
    public void run() {
        try {
            hashFile();
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
