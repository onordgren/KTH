package kth.projects.uppgift8;

import java.io.*;

/**
 * Searches the input file (or standard input if no file is given)
 * for lines containing the given pattern and prints these lines.
 * <p>
 * Usage: java Grep PATTERN [FILE]
 * 
 * @author Stefan Nilsson
 * @version 2010-02-24
 */
public final class Grep {
    private final static String NAME = "Grep";
    private final static String USAGE =
        "Usage: java " + NAME + " PATTERN [FILE]";
    private final static String STDIN = "stdin";
    private final static String OPEN_ERR = NAME + ": cannot find ";
    private final static String READ_ERR = NAME + ": error reading ";
    private final static String CLOSE_ERR = NAME + ": error closing ";

    private Grep() {}

    /**
     * Searches the input file (args[1] or standard input if
     * no file is given) for lines containing the given pattern
     * (args[0]) and prints these lines.
     */
    public static void main(String[] args) {
        System.exit((new Grep()).work(args) ? 0 : 1);
    }

    /**
     * Does the main work and returns true iff successful.
     */
    private boolean work(String[] args) {
        if (args.length == 0 || args.length > 2) {
            System.err.println(USAGE);
            return false;
        }
        final String pattern = args[0];
        final String fileName =
            args.length == 1 ? STDIN : args[1];

        BufferedReader in = null;
        try {
            if (args.length == 1) {
                in = new BufferedReader(
                        new InputStreamReader(System.in));
            } else {
                in = new BufferedReader(
                        new FileReader(fileName));
            }
            search(pattern, in);
        } catch (FileNotFoundException e) {
            System.err.println(OPEN_ERR + fileName);
            return false;
        } catch (IOException e) {
            System.err.println(READ_ERR + fileName);
            return false;
        } finally {
            try {
                if (in != null) 
                    in.close();
            } catch (IOException e) {
                System.err.println(CLOSE_ERR + fileName);
                return false;
            }
        }
        return true;
    }

    /** 
     * Prints all lines in the stream that contain the pattern.
     */
    private void search(String pattern, BufferedReader in)
    throws IOException {
        String line;
        while ((line = in.readLine()) != null) {
            if (line.indexOf(pattern) != -1)
                System.out.println(line);
        }
    }
}