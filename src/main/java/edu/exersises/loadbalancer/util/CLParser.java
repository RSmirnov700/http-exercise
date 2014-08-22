package edu.exersises.loadbalancer.util;

import org.apache.commons.cli.*;
import org.apache.commons.cli.CommandLine;

/**
 * Created by rsmirnov on 8/1/14.
 */
public class CLParser {
    public static final String CONFIG_PATH = "configPath";
    private static CLParser instance = new CLParser();
    private final Options options = new Options();
    private CommandLineParser parser = new PosixParser();
    private CommandLine cmdLine = null;
    private HelpFormatter help = new HelpFormatter();


    private CLParser() {
        options.addOption(
                OptionBuilder.hasArg()
                        .withArgName(CONFIG_PATH)
                        .withType(String.class)
                        .withDescription("Specify full qualified path to application config file")
                        .create(CONFIG_PATH));
    }
    public static CLParser getInstance() {
        return  instance;
    }

    public boolean parseAndValidateCommandLine(String[] args) {
        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return cmdLine.hasOption(CONFIG_PATH);
    }


    public void printHelpMessage() {
        help.printHelp("syntax",options);
    }

    public CommandLine getCmdLine() {
        return cmdLine;
    }
}
