package edu.exersises.loadbalancer.lb;

import edu.exersises.loadbalancer.lb.strategies.LBStrategy;
import edu.exersises.loadbalancer.util.edu.exersises.loadbalancer.configdata.BackendData;
import edu.exersises.loadbalancer.util.edu.exersises.loadbalancer.configdata.DirectorData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rsmirnov on 8/13/14.
 */
public class Director {
    private LBStrategy strategy;
    private DirectorData data;
    private List<Backend> backends;


    public Director(DirectorData data) {
        this.data = data;
        backends = new ArrayList<Backend>(data.getBackends().size());
        for(BackendData bd : data.getBackends()) {
            backends.add(new Backend(bd));
        }
    }


    public String dispatch() {
        synchronized (this.getBackends()) {
            return this.strategy.dispatch(this);
        }
    }

    public List<Backend> getBackends() {
        return backends;
    }

    public String getName() {
        return data.getName();
    }

    public void setBackends(List<Backend> backends) {
        this.backends = backends;
    }

    public LBStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(LBStrategy strategy) {
        this.strategy = strategy;
    }
}
