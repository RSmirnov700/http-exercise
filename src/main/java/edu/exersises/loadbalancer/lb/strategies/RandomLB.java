package edu.exersises.loadbalancer.lb.strategies;

import edu.exersises.loadbalancer.lb.Backend;
import edu.exersises.loadbalancer.lb.Director;

import java.util.Random;

/**
 * Created by rsmirnov on 8/13/14.
 */
public class RandomLB implements LBStrategy {

    private static final RandomLB instance = new RandomLB();

    public static RandomLB getInstance() {
        return instance;
    }

    private RandomLB() {
    }

    private Random random = new Random();

    @Override
    public String dispatch(Director director) {
        Backend backend = null;
        StringBuffer result = new StringBuffer("Request is processed by director: ")
                .append(director.getName()).append("<br>")
                .append("Mode: random<br>");
        synchronized (random) {
            backend = director.getBackends().get(random.nextInt(director.getBackends().size()));
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
