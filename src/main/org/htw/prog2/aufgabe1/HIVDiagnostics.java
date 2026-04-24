package org.htw.prog2.aufgabe1;
import org.apache.commons.cli.*;

public class HIVDiagnostics {

    /**
     * Parst die Kommandozeilenargumente. Gibt null zurück, falls:
     * <ul>
     *     <li>Ein Fehler beim Parsen aufgetreten ist (z.B. eins der erforderlichen Argumente nicht angegeben wurde)</li>
     *     <li>Bei -m, -d und -r nicht die gleiche Anzahl an Argumenten angegeben wurde</li>
     * </ul>
     * @param args Array mit Kommandozeilen-Argumenten
     * @return CommandLine-Objekt mit geparsten Optionen
     */

    public static CommandLine parseOptions(String[] args) {
        Options options = new Options();

        // Define required options
        options.addRequiredOption("m", "mutationfiles", true, "Path to CSV file with mutation patterns");
        options.addRequiredOption("d", "drugnames", true, "Name of the drug");
        options.addRequiredOption("r", "references", true, "Path to FASTA file with reference sequence");
        options.addRequiredOption("p", "patientseqs", true, "Path to FASTA file with patient sequences");

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            CommandLine cmd = parser.parse(options, args);
            return cmd;
        } catch (MissingOptionException e) {
            formatter.printHelp("HIVDiagnostics", options);
            return null;
        } catch (ParseException e) {
            formatter.printHelp("HIVDiagnostics", options);
            return null;
        }
    }

    public static void main(String[] args) {
        // Parse command line options
        CommandLine cmd = parseOptions(args);
        if (cmd == null) {
            return;
        }

        // Get option values
        String mutationFile = cmd.getOptionValue("m");
        String drugName = cmd.getOptionValue("d");
        String referenceFile = cmd.getOptionValue("r");
        String patientSeqFile = cmd.getOptionValue("p");

        // Load reference sequence
        System.out.println("Loading reference sequence from: " + referenceFile);
        SeqFile referenceSeq = new SeqFile(referenceFile);
        if (!referenceSeq.isValid()) {
            System.err.println("Error: Could not read reference sequence file.");
            return;
        }
        System.out.println("Reference sequence loaded successfully.");
        System.out.println("First sequence: " + referenceSeq.getFirstSequence());

        // Load patient sequences
        System.out.println("\nLoading patient sequences from: " + patientSeqFile);
        SeqFile patientSeqs = new SeqFile(patientSeqFile);
        if (!patientSeqs.isValid()) {
            System.err.println("Error: Could not read patient sequences file.");
            return;
        }
        System.out.println("Patient sequences loaded successfully.");
        System.out.println("Number of sequences: " + patientSeqs.getNumberOfSequences());

        // Display drug name and mutation file
        System.out.println("\nDrug: " + drugName);
        System.out.println("Mutation file: " + mutationFile);
    }
}
