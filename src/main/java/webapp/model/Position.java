package webapp.model;

import webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import static webapp.model.Section.EMPTY_DATE;
import static webapp.model.Section.EMPTY_STRING;

@XmlAccessorType(XmlAccessType.FIELD)
public class Position implements Serializable {
    // Data structure
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate startDate;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate endDate;
    private String positionName;
    private String description;

    public static final Position EMPTY = new Position();

    //   public Position() {
//        this(EMPTY_DATE, EMPTY_DATE, EMPTY_STRING, EMPTY_STRING);
//    }
    public Position() {
    }

    public Position(LocalDate startDate, LocalDate endDate, String positionName, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(positionName, "title must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.positionName = positionName;
        this.description = description == null ? EMPTY_STRING : description;
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