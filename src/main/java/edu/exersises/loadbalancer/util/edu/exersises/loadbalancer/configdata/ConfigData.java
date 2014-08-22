package edu.exersises.loadbalancer.util.edu.exersises.loadbalancer.configdata;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by rsmirnov on 7/31/14.
 */
public class ConfigData {
    private int listen_port;
    private int initial_threads;
    private int max_threads;
    private Map<String, BackendData> backends = new HashMap<String, BackendData>();
    private Map<String, DirectorData> directors = new HashMap<String, DirectorData>();
    private Map<String, RuleData> rules = new HashMap<String, RuleData>();


    public int getListen_port() {
        return listen_port;
    }

    public void setListen_port(int listen_port) {
        this.listen_port = listen_port;
    }

    public int getInitial_threads() {
        return initial_threads;
    }

    public void setInitial_threads(int initial_threads) {
        this.initial_threads = initial_threads;
    }

    public int getMax_threads() {
        return max_threads;
    }

    public void setMax_threads(int max_threads) {
        this.max_threads = max_threads;
    }

    public Map<String, DirectorData> getDirectors() {
        return directors;
    }

    public void setDirectors(Map<String, DirectorData> directors) {
        this.directors = directors;
    }

    public Map<String, RuleData> getRules() {
        return rules;
    }

    public void setRules(Map<String, RuleData> rules) {
        this.rules = rules;
    }

    public Map<String, BackendData> getBackends() {
        return backends;
    }

    public void setBackends(Map<String, BackendData> backends) {
        this.backends = backends;
    }
}
