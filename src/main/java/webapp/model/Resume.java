package webapp.model;
import java.util.UUID;

/*** Resume class */
public class Resume  implements Comparable<Resume>  {
    // Resume = structure of
    //          uuid     string
    //          fullName string
public  UUID   uuid;
public  String fullName;

    // Constructor One = empty resume
public Resume() {
    this.fullName="empty";
    this.uuid = UUID.randomUUID();
}
   // Constructor Two = new resume for new fullname, UUID created randomly (type 4) and automatically
public Resume(String fullName) {
        this.fullName=fullName;
        this.uuid = UUID.randomUUID();
}
  // Constructor Three =
public Resume(UUID uuid, String fullName) {
        this.fullName=fullName;
        this.uuid = uuid;
    }

    public Resume(UUID uuid)  {
    this.fullName="empty";
    this.uuid = uuid;
    }

    // Methods
    public UUID getUuid() { return uuid; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid);
    }
    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
    @Override
    public String toString() {
        return String.valueOf(uuid);
    }
    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }
}
