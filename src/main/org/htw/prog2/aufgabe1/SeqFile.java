package org.htw.prog2.aufgabe1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class SeqFile {
    /**
     * Reads the specified FASTA file and stores sequences. In case the file does not exist or is not a valid FASTA
     * file, the Constructor does not throw an Exception. Instead, isValid() on the resulting object will return false.
     * @param filename "The path to the fasta file"
     */
    public SeqFile(String filename) {
        readFile(filename);
    }

    /**
     * Contains a List of Sequences.
     */
    private HashSet<String> sequences = new HashSet<>();

    /**
     * Contains a value that determines whether the file was read successfully.
     */
    private boolean wasSuccessful = false;

    /**
     * Contains the first sequence read from the file.
     */
    private String firstSequence = "";

    /**
     * Reads the specified FASTA file.
     * @param filename The path to the FASTA file
     * @return false if the file could not be parsed (wrong format, does not exist), true otherwise.
     */
    private boolean readFile(String filename) {

        try (BufferedReader fastaFileReader = new BufferedReader(new FileReader(filename))) {
            StringBuilder seq = new StringBuilder();
            String line;

            while ((line = fastaFileReader.readLine()) != null) {
                if (line.startsWith(">")) {
                    // If we have a sequence, add it
                    if (seq.length() > 0) {
                        addSequence(seq);
                        seq = new StringBuilder();
                    }
                } else {
                    // Append sequence data
                    seq.append(line);
                }
            }

            // Add the last sequence if any
            if (seq.length() > 0) {
                addSequence(seq);
            }
        }
        catch (IOException ioException) {
            return false;
        }

        this.wasSuccessful = true;
        return true;
    }

    /**
     * Adds the sequence in the passed StringBuilder to the internal hash set and also sets the first sequence if it
     * is still empty.
     * @param seq SequenceBuilder to get the sequence from.
     * @return The length of the added sequence.
     */
    private int addSequence(StringBuilder seq) {
        String sequenceStr = seq.toString();
        sequences.add(sequenceStr);

        // Set first sequence if it's still empty
        if (firstSequence.isEmpty()) {
            firstSequence = sequenceStr;
        }

        return seq.length();
    }

    /**
     *
     * @return The number of sequences read from the FASTA file, or 0 if isValid() is false.
     */
    public int getNumberOfSequences() {
        if (!isValid()) {
            return 0;
        }
        return sequences.size();
    }

    /**
     *
     * @return The sequences read from the FASTA file, or an empty HashSet if isValid() is false.
     */
    public HashSet<String> getSequences() {
        if (!isValid()) {
            return new HashSet<>();
        }
        return sequences;
    }

    /**
     *
     * @return The first sequence read from the FASTA file, or an empty String if isValid() is false.
     */
    public String getFirstSequence() {
        if (!isValid()) {
            return "";
        }
        return firstSequence;
    }

    /**
     *
     * @return true if the FASTA file was read successfully, false otherwise.
     */
    public boolean isValid() {
        return this.wasSuccessful;
    }
}
