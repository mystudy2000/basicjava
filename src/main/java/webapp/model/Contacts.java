package webapp.model;
import java.util.EnumMap;

class Contacts implements Base{
    // Data structure
    private TypeOfContact contactType;
    private        String contactValue;
    // Data type
    EnumMap<TypeOfContact, String> contacts = new EnumMap<>(TypeOfContact.class);
    // Telescopic Constructors
    Contacts() {
        this.contactType=TypeOfContact.EMPTY;
        this.contactValue=EMPTY_STRING;}
    Contacts(TypeOfContact type, String contactValue) {
        this.contactType=type;
        this.contactValue=contactValue;}
}
