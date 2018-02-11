package webapp.model;

import java.util.UUID;

/*** Resume class */
public class Resume implements Comparable<Resume>, Base {
/**  Resume = structure of
             uuid            UUID
             fullName        string
             Contacts        EnumMap < TypeOfContact, String>
             resumeSection   EnumMap < TypeOfSection, Object>
 */
    public UUID          uuid;
    public String        fullName;
    public Contacts      contacts;
    public Sections      section;

    public Resume() {
        this.uuid =  UUID.randomUUID();
        this.fullName =   EMPTY_STRING;
        this.contacts = new Contacts();
        this.section  = new Sections();
    }

    public Resume(String fullName) {
        this (UUID.randomUUID(), fullName);
        this.contacts = new Contacts();
        this.section  = new Sections();
    }

    public Resume(UUID uuid, String fullName) {
        this.fullName=fullName;
        this.uuid = uuid;
        this.contacts = new Contacts();
        this.section  = new Sections();
    }

    // Resume getters
    public UUID          getUuid()   { return uuid; }
    public String    getFullName() {return fullName;}
    public Contacts  getContacts() {return contacts;}
    public Sections   getSection()  {return section;}

    @Override
    public String toString() {
        return " UUID:"+uuid+" fullName:"+fullName;
    }

    @Override
    public int compareTo(Resume o) {
        int result;
        result=uuid.compareTo(o.uuid);
        if(result != 0) return result;
        result=fullName.compareTo(o.fullName);
        if(result != 0) return result;
        return 0;
    }

}
