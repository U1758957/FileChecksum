package controller;

import hash.Hash;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Controls the hash workers
 */
public class HashController {

    private String[] algorithms;
    private File file;

    /**
     * The constructor
     *
     * @param algorithms The algorithms for hashing
     * @param file       The file for hashing
     */
    public HashController(String[] algorithms, File file) {
        this.algorithms = algorithms;
        this.file = file;
    }

    /**
     * Return a dictionary of the hashing algorithm (key) and the checksum it hashes (value), and control the running
     * of the hashes
     *
     * @return a dictionary of hash results
     * @throws InterruptedException if a thread is interrupted
     */
    public Map<String, String> runAndGetHashes() throws InterruptedException {

        Map<String, String> finishedHashes = new HashMap<>();

        int threadCount = algorithms.length;

        Hash[] hashes = new Hash[threadCount];

        for (int x = 0; x < threadCount; x++) {
            hashes[x] = new Hash(algorithms[x], file);
            hashes[x].t.start();
        }

        for (int x = 0; x < threadCount; x++) {
            hashes[x].t.join();
            finishedHashes.put(algorithms[x], hashes[x].getHashResult());
        }

        return finishedHashes;

    }

}
