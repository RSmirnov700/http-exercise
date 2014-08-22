package edu.exersises.loadbalancer.lb;

import edu.exersises.loadbalancer.util.edu.exersises.loadbalancer.configdata.BackendData;

/**
 * Created by rsmirnov on 8/13/14.
 */
public class Backend {
    private BackendData data;

    public Backend(BackendData data) {
        this.data = data;
    }

    public String getName() {
        return data.getName();
    }

    public String getIp() {
        return data.getIp();
    }

}
