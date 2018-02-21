package webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static webapp.model.Section.EMPTY_STRING;

class Organization {
    public static final Organization EMPTY = new Organization(EMPTY_STRING, Position.EMPTY);
    // Data structure
    private String name;
    private List<Position> positions;

    // Constructors
    Organization() {
        this.name = EMPTY_STRING;
        this.positions = (List<Position>) Position.EMPTY;
    }

    Organization(String name, Position... positions) {
        this(name, Arrays.asList(positions));
    }

    Organization(String name, List<Position> positions) {
        this.name = name;
        this.positions = positions;
    }

    public String getName() {
        return name;
    }

    public List<Position> getPositions() {
        return positions;
    }

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

