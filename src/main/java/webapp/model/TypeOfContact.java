package webapp.model;

public enum TypeOfContact {
    //   EMPTY("EMPTY"),
    HOMEPHONE("Телефон домашний"),
    MOBILEPHONE("Телефон мобильный"),
    MAIL("E-MAIL") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink("mailto:" + value, value);
        }
    },
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink("skype:" + value, value);
        }
    },
    LINKEDIN("Профиль Linkedin") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    STACKOVERFLOW("Профиль StackOverflow ") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    GITHUB("Профиль GitHub") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    HOMEPAGE("Домашняя страница") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    };


    private final String title;

    TypeOfContact(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml5(String value) {

        if (value == null) {
            return "Отсутствует";
        } else return value;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    public String toLink(String href) {
        return toLink(href, title);
    }

    public static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }
}
