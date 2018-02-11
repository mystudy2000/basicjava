package webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization extends Position {
    // Data structure
    private String name;
    private List<Position> positions = new ArrayList<>();
    // Constructors
    Organization() {this.name=EMPTY_STRING;this.positions.add(new Position());}
    Organization(String name, Position... positions) { this(name, Arrays.asList(positions)); }
    Organization(String name, List<Position> positions) {this.name=name; this.positions=positions;}

    // getter
    public List<Position> getPositions() {return positions;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization that = (Organization) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, positions);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", positions=" + positions +
                '}';
    }
}