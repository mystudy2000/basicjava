package webapp.model;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

public class Sections implements Base{

    private       TypeOfSection   sectionType;
    private       StringSection    objective = new StringSection();
    private       StringSection      personal = new StringSection();
    private         ListSection   achievements = new ListSection();
    private         ListSection qualification = new ListSection();
    private OrganizationSection    experience = new OrganizationSection();
    private OrganizationSection     education = new OrganizationSection();

    Sections(){
        EnumMap<TypeOfSection, Object>   sections = new EnumMap(TypeOfSection.class){{
            put(TypeOfSection.OBJECTIVE,          objective);
            put(TypeOfSection.PERSONAL,            personal);
            put(TypeOfSection.ACHIEVEMENT,     achievements);
            put(TypeOfSection.QUALIFICATIONS, qualification);
            put(TypeOfSection.EXPERIENCE,        experience);
            put(TypeOfSection.EDUCATION,          education);
        }};
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sections)) return false;
        Sections sections1 = (Sections) o;
        return sectionType == sections1.sectionType &&
                Objects.equals(objective, sections1.objective) &&
                Objects.equals(personal, sections1.personal) &&
                Objects.equals(achievements, sections1.achievements) &&
                Objects.equals(qualification, sections1.qualification) &&
                Objects.equals(experience, sections1.experience) &&
                Objects.equals(education, sections1.education);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionType, objective, personal, achievements, qualification, experience, education);
    }

    @Override
    public String toString() {
        return "Sections{" +
                "sectionType=" + sectionType +
                ", objective=" + objective +
                ", personal=" + personal +
                ", achievements=" + achievements +
                ", qualification=" + qualification +
                ", experience=" + experience +
                ", education=" + education +
                '}';
    }

    /*********************************************************************************************************************/
    class StringSection {
        // Data structure
        private String description;
        // Constructors
        StringSection() {this.description = EMPTY_STRING;}
        StringSection(String description) {this.description = description;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringSection)) return false;
        StringSection that = (StringSection) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(description);
    }

    @Override
    public String toString() {
        return "StringSection{}";
    }
}
/*********************************************************************************************************************/
    class ListSection {
        // Data structure
        private List<String> items;
        // Constructors
        ListSection() {this.items.add(EMPTY_STRING);}
        ListSection(String... itemsStrings) {this(Arrays.asList(itemsStrings));}
        ListSection(List<String> itemsList) {this.items = itemsList;}
        // getter
        List<String> getItems() {return items;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListSection)) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {

        return Objects.hash(items);
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "items=" + items +
                '}';
    }
}
/*********************************************************************************************************************/
    class OrganizationSection extends Organization{
        // Data structure
        private List<Organization> organizations;
        // Constructors
        OrganizationSection() {this.organizations.add(new Organization());}
        OrganizationSection(Organization ... organizations) {this(Arrays.asList(organizations));}
        OrganizationSection(List<Organization> organizations) {this.organizations = organizations;}
        // Getter
        public List<Organization> getOrganizations() {return organizations;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationSection)) return false;
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(organizations, that.organizations);
    }

    @Override
    public int hashCode() {

        return Objects.hash(organizations);
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "organizations=" + organizations +
                '}';
    }
}
}