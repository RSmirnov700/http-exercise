package edu.exersises.loadbalancer.util.edu.exersises.loadbalancer.configdata;

import java.util.List;

/**
 * Created by rsmirnov on 7/31/14.
 */
public class DirectorData {
    private String name;
    private String lb_method;
    private List<BackendData> backends;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLb_method() {
        return lb_method;
    }

    public void setLb_method(String lb_method) {
        this.lb_method = lb_method;
    }

    public List<BackendData> getBackends() {
        return backends;
    }

    public void setBackends(List<BackendData> backends) {
        this.backends = backends;
    }
}
