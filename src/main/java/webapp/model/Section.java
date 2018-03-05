package webapp.model;

import java.io.Serializable;
import java.time.LocalDate;

import static java.time.LocalDate.MIN;

abstract public class Section implements Serializable {
    private static final long serialVersionUID = 1L;
    static final String EMPTY_STRING = "EMPTY STRING";
    static final LocalDate EMPTY_DATE = MIN;
}
