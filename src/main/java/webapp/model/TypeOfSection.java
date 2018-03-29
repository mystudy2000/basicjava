package webapp.model;

public enum TypeOfSection {
    EMPTY("EMPTY") {},
    OBJECTIVE("Позиция") {},
    PERSONAL("Личные качества") {},
    ACHIEVEMENT("Достижения") {},
    QUALIFICATIONS("Квалификация") {},
    EXPERIENCE("Опыт работы") {},
    EDUCATION("Образование") {};

    private final String title;

    TypeOfSection(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
