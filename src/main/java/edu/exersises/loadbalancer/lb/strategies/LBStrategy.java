package edu.exersises.loadbalancer.lb.strategies;

import edu.exersises.loadbalancer.lb.Director;

/**
 * Created by rsmirnov on 8/13/14.
 */
public interface LBStrategy {
    public String dispatch(Director director);
}
