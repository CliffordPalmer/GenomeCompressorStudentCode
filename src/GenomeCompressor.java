/******************************************************************************
 *  Compilation:  javac GenomeCompressor.java
 *  Execution:    java GenomeCompressor - < input.txt   (compress)
 *  Execution:    java GenomeCompressor + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   genomeTest.txt
 *                virus.txt
 *
 *  Compress or expand a genomic sequence using a 2-bit code.
 ******************************************************************************/

/**
 *  The {@code GenomeCompressor} class provides static methods for compressing
 *  and expanding a genomic sequence using a 2-bit code.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 */
public class GenomeCompressor {

    /**
     * Reads a sequence of 8-bit extended ASCII characters over the alphabet
     * { A, C, T, G } from standard input; compresses and writes the results to standard output.
     */
    public static void compress() {
        // Map for constant time compression of each letter
        int[] alphabetReference = new int[256];
        alphabetReference['A'] = 0;
        alphabetReference['C'] = 1;
        alphabetReference['T'] = 2;
        alphabetReference['G'] = 3;

        // Read in the DNA sequence
        String sequence = BinaryStdIn.readString();

        // Use the first 32 bits to store the length of the sequence. Solves an issue where the file needs to
        // be a certain amount of bytes long, and any remaining space was being filled with zeros, and those
        // getting expanded later.
        BinaryStdOut.write(sequence.length());

        // Loop through the sequence assigning each letter to its equivalent integer value
        for(int i = 0; i < sequence.length(); i++){
            BinaryStdOut.write(Character.toUpperCase(alphabetReference[sequence.charAt(i)]), 2);
        }

        BinaryStdOut.close();
    }

    /**
     * Reads a binary sequence from standard input; expands and writes the results to standard output.
     */
    public static void expand() {
        // Map for constant time expansion of each compressed letter
        char[] alphabetReference = new char[256];
        alphabetReference[0] = 'A';
        alphabetReference[1] = 'C';
        alphabetReference[2] = 'T';
        alphabetReference[3] = 'G';

        // Read the length of the sequence from the first 32 bits
        int sequenceLength = BinaryStdIn.readInt();
        // Loop through the rest of the file, expanding each int into its corresponding letter
        for(int i = 0; i < sequenceLength; i++){
            BinaryStdOut.write(alphabetReference[BinaryStdIn.readInt(2)]);
        }
        BinaryStdOut.close();
    }


    /**
     * Main, when invoked at the command line, calls {@code compress()} if the command-line
     * argument is "-" an {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}