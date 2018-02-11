package webapp.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static java.time.LocalDate.MIN;

public interface Base {
    String EMPTY_STRING = "EMPTY STRING";
    List<String> EMPTY_LIST = Collections.singletonList("EMPTY_LIST");
    LocalDate EMPTY_DATE = MIN;
}
