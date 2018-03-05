package webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private static final long serialVersionUID = 1L;
    public static final ListSection EMPTY = new ListSection(EMPTY_STRING);
    private List<String> listSection;

    public List<String> getListSection() {
        return listSection;
    }

    public ListSection(List<String> listSection) {
        this.listSection = listSection;
    }

    public ListSection(String... listSection) {
        this(Arrays.asList(listSection));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListSection)) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(getListSection(), that.getListSection());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getListSection());
    }

    @Override
    public String toString() {
        return listSection.toString();
    }
}
