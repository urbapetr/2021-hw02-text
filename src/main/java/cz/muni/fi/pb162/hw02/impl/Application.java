package cz.muni.fi.pb162.hw02.impl;

import com.beust.jcommander.Parameter;
import cz.muni.fi.pb162.hw02.FileLoader;
import cz.muni.fi.pb162.hw02.LinesSaver;
import cz.muni.fi.pb162.hw02.Messages;
import cz.muni.fi.pb162.hw02.cmd.CommandLine;

import java.io.IOException;

/**
 * Application class represents the command line interface of this application.
 * <p>
 * You are expected to implement  the  {@link Application#run(CommandLine)} method
 *
 * @author jcechace
 */
public class Application {

    @Parameter(names = { "-u" }, description = "Filter unique lines")
    private boolean unique = false;

    @Parameter(names = { "-s" }, description = "Sort lines by natural ordering")
    private boolean sort = false;

    @Parameter(names = { "-d" }, description = "Filter duplicate lines")
    private boolean duplicates = false;

    @Parameter(names = { "lines" }, description = "Print lines (default operation)")
    private boolean lines = true;

    @Parameter(names = { "count" }, description = "Count lines")
    private boolean count = false;

    @Parameter(names = { "sizes" }, description = "Counts the characters for each line (excluding line separators)")
    private boolean sizes = false;

    @Parameter(names = { "similar" },
            description = "Lists pairs of most similar (distinct) lines according to Levenshtein distance")
    private boolean similar = false;

    @Parameter(names = "--help", help = true, description = "Print application usage")
    private boolean showUsage = false;

    @Parameter(names = "--file", description = "Path to file operated on by the application")
    private String file = "";

    /**
     * Application entry point
     *
     * @param args command line arguments of the application
     */
    public static void main(String[] args) {
        Application app = new Application();

        CommandLine cli = new CommandLine(app);
        cli.parseArguments(args);

        if (app.showUsage) {
            cli.showUsage();
        } else {
            app.run(cli);
        }
    }

    /**
     * Application runtime logic
     *
     * @param cli command line interface
     */
    private void run(CommandLine cli) {
        FileLoader myFile = new FileLoader();
        try {
            LinesSaver mySaver = new LinesSaver(myFile.loadAsLines(file));
            if (duplicates && unique) {
                System.err.println(Messages.INVALID_OPTION_COMBINATION);
            }
            if (unique) {
                mySaver.unique();
            }
            if (sort) {
                mySaver.sort();
            }
            if (duplicates) {
                mySaver.duplicates();
            }
            if (count) {
                System.out.printf("%d", mySaver.getCounter());
            }
            if (sizes) {
                mySaver.sizes();
            }
            if (similar) {
                mySaver.similar();
            }
            if (!count && !similar && !sizes) {
                mySaver.lines();
            }
        } catch (IOException ex) {
            System.err.printf(Messages.IO_ERROR, file);
        }
    }
}
