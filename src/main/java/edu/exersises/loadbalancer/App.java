package edu.exersises.loadbalancer;

import com.esotericsoftware.yamlbeans.YamlReader;
import edu.exersises.loadbalancer.edu.exersises.loadbalancer.http.DumbHttpServer;
import edu.exersises.loadbalancer.exception.InvalidConfigurationException;
import edu.exersises.loadbalancer.util.CLParser;
import edu.exersises.loadbalancer.util.ConfigParser;
import edu.exersises.loadbalancer.util.edu.exersises.loadbalancer.configdata.ConfigData;
import org.apache.commons.cli.*;

import java.text.ParseException;

/**
 * Created by rsmirnov on 7/31/14.
 */
public class App {

    public static void main(String[] args) {
        if (!CLParser.getInstance().parseAndValidateCommandLine(args)) {
            CLParser.getInstance().printHelpMessage();
            System.exit(-1);
        }
        String configLocation = CLParser.getInstance().getCmdLine().getOptionValue(CLParser.CONFIG_PATH);

        try {
            ConfigData data = ConfigParser.getInstance().parseConfig(configLocation);
            Thread serverThread = new Thread(new DumbHttpServer(data));
            serverThread.start();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
