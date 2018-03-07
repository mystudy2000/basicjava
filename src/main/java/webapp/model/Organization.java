package webapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Organization EMPTY = new Organization();
    // Data structure
    private Link homePage;
    private List<Position> positions = new ArrayList();

    // Constructors
    Organization() {
        this.positions.add(new Position());
        this.homePage=new Link();
    }

    public Organization(String name, String url,  Position... positions) {
        this(new Link(name,url), Arrays.asList(positions));
    }

    public Organization(Link homePage, List<Position> positions) {
        this.homePage = homePage;
        this.positions = positions;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization that = (Organization) o;
        return Objects.equals(getHomePage(), that.getHomePage()) &&
                Objects.equals(getPositions(), that.getPositions());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getHomePage(), getPositions());
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", positions=" + positions +
                '}';
    }
}

