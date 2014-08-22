package edu.exersises.loadbalancer.lb.strategies;

import edu.exersises.loadbalancer.lb.Backend;
import edu.exersises.loadbalancer.lb.Director;

/**
 * Created by rsmirnov on 8/13/14.
 */
public class RoundRobinLB implements LBStrategy {
    private static final RoundRobinLB instance = new RoundRobinLB();

    private Integer idx = -1;


    public static RoundRobinLB getInstance() {
        return instance;
    }

    private RoundRobinLB() {
    }

    @Override
    public String dispatch(Director director) {
        Backend backend = null;
        StringBuffer result = new StringBuffer("Request is processed by director: ")
                .append(director.getName()).append("<br>")
                .append("Mode: Round robin<br>");
        synchronized (idx) {
            Integer bSize = director.getBackends().size();
            if (idx < 0 || idx >= bSize) {
                idx = 0;
            }
            backend = director.getBackends().get(idx);
            idx++;
        }
        if (backend != null) {
            result.append("Served by backend: <br>Name:").append(backend.getName())
                    .append("<br>").append("IP").append(backend.getIp()).append("<br>");
        } else {
            result.append("Unable to identify backend to serve request");
        }
        return result.toString();
    }
}
