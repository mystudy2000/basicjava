package webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

import static webapp.model.Section.EMPTY_STRING;

@XmlAccessorType(XmlAccessType.FIELD)
public class Link implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String url;

    public Link(String name, String url) {
        this.name = name == null ? EMPTY_STRING : name;
        this.url = url == null ? EMPTY_STRING : url;
    }

    public Link(String name) {
        this(name, EMPTY_STRING);
    }

    public Link() {
        this(EMPTY_STRING, EMPTY_STRING);
    }


    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Link(" + name + ',' + url + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        return name.equals(link.name) && (url != null ? url.equals(link.url) : link.url == null);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}