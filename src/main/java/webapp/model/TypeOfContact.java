package webapp.model;

public enum TypeOfContact {
    EMPTY("EMPTY"),
    HOMEPHONE("Тлф. дом."),
    MOBILEPHONE("Тлф. моб."),
    SKYPE("Skype");
    private final String title;
    TypeOfContact(String title) {this.title = title;}
    public String getTitle() {return title;}
}

