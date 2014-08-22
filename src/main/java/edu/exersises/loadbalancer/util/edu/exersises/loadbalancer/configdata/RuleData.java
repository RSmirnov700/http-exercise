package edu.exersises.loadbalancer.util.edu.exersises.loadbalancer.configdata;

/**
 * Created by rsmirnov on 7/31/14.
 */
public class RuleData {
    private String url;
    private DirectorData director;
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DirectorData getDirector() {
        return director;
    }

    public void setDirector(DirectorData director) {
        this.director = director;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
