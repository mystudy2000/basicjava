package webapp.util;
import webapp.model.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

    public class TestDataOneResume {
        public static final UUID UUID_1 = UUID.randomUUID();

        public static final Resume R1;


        static {
            R1 = new Resume(UUID_1, "Name1");


            R1.setContact(TypeOfContact.MAIL, "mail1@mail.ru");
            R1.setContact(TypeOfContact.HOMEPHONE, "9999999");


            R1.setSection(TypeOfSection.OBJECTIVE, new StringSection("Objective1"));
            R1.setSection(TypeOfSection.PERSONAL, new StringSection("Personal data"));
            R1.setSection(TypeOfSection.ACHIEVEMENT, new ListSection("Achivment11", "Achivment12", "Achivment13"));
            R1.setSection(TypeOfSection.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
            R1.setSection(TypeOfSection.EXPERIENCE,
                    new OrganizationSection(
                            new Organization("Organization11", "https://Organization11.ru",
                                    new Position(LocalDate.parse("2005-10-20"),LocalDate.parse("2010-05-11"), "position1", "content1"),
                                    new Position(LocalDate.parse("2001-01-12"),LocalDate.parse("2005-10-19"), "position2", "content2"))));
            R1.setSection(TypeOfSection.EXPERIENCE,
                    new OrganizationSection(
                            new Organization("Organization2", "https://Organization2.ru",
                                    new Position(LocalDate.parse("2010-06-12"),LocalDate.parse("2017-10-19"), "position1", "content1"))));
            R1.setSection(TypeOfSection.EDUCATION,
                    new OrganizationSection(
                            new Organization("Institute", null,
                                    new Position(LocalDate.parse("1996-06-12"),LocalDate.parse("1997-10-19"), "aspirant", null),
                                    new Position(LocalDate.parse("1991-09-02"),LocalDate.parse("1996-05-20"), "student", "IT faculty")),
                            new Organization("Organization12", "http://Organization12.ru")));

        }
    }
