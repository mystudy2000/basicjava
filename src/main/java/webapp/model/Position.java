package webapp.model;

import java.time.LocalDate;
import java.util.Objects;

import static webapp.model.Section.EMPTY_DATE;
import static webapp.model.Section.EMPTY_STRING;

class Position {
    public static final Position EMPTY = new Position();
    // Data structure
    private LocalDate startDate;
    private LocalDate endDate;
    private String positionName;
    private String description;

    // Telescoping constructors
    Position() {
        this.startDate = EMPTY_DATE;
        this.endDate = EMPTY_DATE;
        this.positionName = EMPTY_STRING;
        this.description = EMPTY_STRING;
    }

    Position(LocalDate startDate, LocalDate endDate, String positionName, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.positionName = positionName;
        this.description = description;
    }

    // Getters
    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return Objects.equals(getStartDate(), position.getStartDate()) &&
                Objects.equals(getEndDate(), position.getEndDate()) &&
                Objects.equals(getPositionName(), position.getPositionName()) &&
                Objects.equals(getDescription(), position.getDescription());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getStartDate(), getEndDate(), getPositionName(), getDescription());
    }

    @Override
    public String toString() {
        return "Position{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", positionName='" + positionName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}