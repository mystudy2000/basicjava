package webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static webapp.model.Section.EMPTY_STRING;

/*** Resume class */
public class Resume implements Comparable<Resume> {
    /**
     * Resume = structure of
     * uuid            UUID
     * fullName        string
     * contacts        EnumMap < TypeOfContact, String>
     * sections        EnumMap < TypeOfSection, Section>
     */
    public static final Resume EMPTY = new Resume();

    static {
        EMPTY.setContact(TypeOfContact.EMPTY, EMPTY_STRING);
        EMPTY.setSection(TypeOfSection.OBJECTIVE, StringSection.EMPTY);
        EMPTY.setSection(TypeOfSection.PERSONAL, StringSection.EMPTY);
        EMPTY.setSection(TypeOfSection.ACHIEVEMENT, ListSection.EMPTY);
        EMPTY.setSection(TypeOfSection.QUALIFICATIONS, ListSection.EMPTY);
        EMPTY.setSection(TypeOfSection.EXPERIENCE, OrganizationSection.EMPTY);
        EMPTY.setSection(TypeOfSection.EDUCATION, OrganizationSection.EMPTY);

    }

    private UUID uuid;
    private String fullName;
    private Map<TypeOfContact, String> contacts = new EnumMap<>(TypeOfContact.class);
    private Map<TypeOfSection, Section> sections = new EnumMap<>(TypeOfSection.class);

    public Resume() {
        this.uuid = UUID.randomUUID();
        this.fullName = EMPTY_STRING;
    }

    public Resume(String fullName) {
        this(UUID.randomUUID(), fullName);
    }

    public Resume(UUID uuid, String fullName) {
        this.fullName = fullName;
        this.uuid = uuid;
    }

    // Resume getters
    public UUID getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<TypeOfContact, String> getContacts() {
        return contacts;
    }

    public Map<TypeOfSection, Section> getSections() {
        return sections;
    }

    public String getContact(TypeOfContact type) {
        return contacts.get(type);
    }

    public Section getSection(TypeOfSection type) {
        return sections.get(type);
    }

    // Resume setters
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setContact(TypeOfContact type, String value) {
        contacts.put(type, value);
    }

    public void setSection(TypeOfSection type, Section section) {
        sections.put(type, section);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume)) return false;
        Resume resume = (Resume) o;
        return Objects.equals(getUuid(), resume.getUuid()) &&
                Objects.equals(getFullName(), resume.getFullName()) &&
                Objects.equals(getContacts(), resume.getContacts()) &&
                Objects.equals(getSections(), resume.getSections());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getFullName(), getContacts(), getSections());
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid=" + uuid +
                ", fullName='" + fullName +
                '}';
    }
}
