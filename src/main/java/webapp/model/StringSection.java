package webapp.model;

import java.util.Objects;

public class StringSection extends Section {
    private static final long serialVersionUID = 1L;
    public static final StringSection EMPTY = new StringSection(EMPTY_STRING);
    private String stringSection;

    public String getStringSection() {
        return stringSection;
    }

    public StringSection(String stringSection) {
        this.stringSection = stringSection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringSection)) return false;
        StringSection that = (StringSection) o;
        return Objects.equals(getStringSection(), that.getStringSection());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getStringSection());
    }

    @Override
    public String toString() {
        return stringSection;
    }
}
