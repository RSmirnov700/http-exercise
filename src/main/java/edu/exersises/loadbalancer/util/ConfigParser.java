package edu.exersises.loadbalancer.util;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import edu.exersises.loadbalancer.exception.InvalidConfigurationException;
import edu.exersises.loadbalancer.util.edu.exersises.loadbalancer.configdata.BackendData;
import edu.exersises.loadbalancer.util.edu.exersises.loadbalancer.configdata.ConfigData;
import edu.exersises.loadbalancer.util.edu.exersises.loadbalancer.configdata.DirectorData;
import edu.exersises.loadbalancer.util.edu.exersises.loadbalancer.configdata.RuleData;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by rsmirnov on 7/31/14.
 */
public class ConfigParser {
    private static volatile ConfigParser instance = null;
    private ConfigParser () {}

    public static ConfigParser getInstance() {
        if(instance == null) {
            synchronized (ConfigParser.class) {
                if (instance == null) {
                    instance = new ConfigParser();
                }
            }
        }
        return instance;
    }

    public ConfigData parseConfig(String configLocation) throws InvalidConfigurationException {
        try {
            YamlReader reader = new YamlReader(new FileReader(configLocation));
            reader.getConfig().setPropertyElementType(ConfigData.class, "backends", BackendData.class);
            reader.getConfig().setPropertyElementType(ConfigData.class,"directors", DirectorData.class);
            reader.getConfig().setPropertyElementType(ConfigData.class,"rules",RuleData.class);
            return reader.read(ConfigData.class);
        } catch (FileNotFoundException e) {
            throw new InvalidConfigurationException("Application config " + configLocation + " not found", e);
        } catch (YamlException e) {
            throw new InvalidConfigurationException("Error while reading config: " + e.getMessage(), e);
        }
    }


}
