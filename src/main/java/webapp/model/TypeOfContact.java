package webapp.model;

public enum TypeOfContact {
    EMPTY("EMPTY"),
    HOMEPHONE("Тлф. дом."),
    MOBILEPHONE("Тлф. моб."),
    MAIL("E-MAIL"),
    LINKEDIN("Профиль Linkedin"),
    STACKOVERFLOW("Профиль StackOverflow "),
    GITHUB("Профиль GitHub"),
    SKYPE("Skype"),
    HOMEPAGE("Домашняя страница");
    private final String title;

    TypeOfContact(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

