package edu.exersises.loadbalancer.lb;

import edu.exersises.loadbalancer.lb.strategies.LBStrategy;
import edu.exersises.loadbalancer.util.edu.exersises.loadbalancer.configdata.ConfigData;
import edu.exersises.loadbalancer.util.edu.exersises.loadbalancer.configdata.DirectorData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rsmirnov on 8/13/14.
 */
public class RequestDispatcher {
    private static RequestDispatcher rd;
    private Map<String, Director> rules = new HashMap<String, Director>();


    public static RequestDispatcher getInstance(ConfigData data) {
        if (rd == null) {
            synchronized (RequestDispatcher.class) {
                if (rd == null) {
                    rd = new RequestDispatcher();
                    for (String rule : data.getRules().keySet()) {
                        DirectorData dd = data.getRules().get(rule).getDirector();
                        Class clazz = null;
                        try {
                            clazz = Class.forName(dd.getLb_method());
                            Method factoryMethod = null;
                            factoryMethod = clazz.getDeclaredMethod("getInstance");
                            LBStrategy strategy = (LBStrategy) factoryMethod.invoke(null, null);
                            Director d = new Director(dd);
                            d.setStrategy(strategy);
                            rd.getRules().put(data.getRules().get(rule).getUrl(), d);
                        } catch (ClassNotFoundException e) {
                            System.out.println("Skipping director: " + dd.getName()
                                    + "initialization: unknown LB strategy class " + dd.getLb_method());
                        } catch (NoSuchMethodException e) {
                            System.out.println("Skipping director: " + dd.getName()
                                    + "initialization: unable to invoke getInstance method on LB strategy class "
                                    + dd.getLb_method());
                        } catch (InvocationTargetException e) {
                            System.out.println("Skipping director: " + dd.getName()
                                    + "initialization: unable to invoke getInstance method on LB strategy class "
                                    + dd.getLb_method());
                        } catch (IllegalAccessException e) {
                            System.out.println("Skipping director: " + dd.getName()
                                    + "initialization: unable to invoke getInstance method on LB strategy class "
                                    + dd.getLb_method());
                        }
                    }
                }
            }
        }
        return rd;
    }

    private RequestDispatcher() {
    }


    public String dispatchRequest(String uri) {
        String dispatchResult = "No directors defined for URI " + uri;
        Director director = getRules().get(uri);
        if (director != null) {
            dispatchResult = director.dispatch();
        }
        return dispatchResult;
    }

    public Map<String, Director> getRules() {
        return rules;
    }

}
