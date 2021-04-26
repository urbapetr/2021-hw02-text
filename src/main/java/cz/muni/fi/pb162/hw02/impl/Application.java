package cz.muni.fi.pb162.hw02.impl;

import com.beust.jcommander.Parameter;
import cz.muni.fi.pb162.hw02.cmd.CommandLine;

/**
 * Application class represents the command line interface of this application.
 * <p>
 * You are expected to implement  the  {@link Application#run(CommandLine)} method
 *
 * @author jcechace
 */
public class Application {

    @Parameter(names = "--help", help = true)
    private boolean showUsage = false;

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
        throw new UnsupportedOperationException("Remove this line and implement the method");
    }
}
