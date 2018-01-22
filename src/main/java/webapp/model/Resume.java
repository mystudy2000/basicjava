package webapp.model;

import java.util.Objects;
import java.util.UUID;

/*** Resume class */
public class Resume  implements Comparable<Resume>  {
//  Resume = structure of
//           uuid     UUID
//           fullName string
    public  UUID   uuid;
    public  String fullName;
//  Constructor One = empty resume
    public Resume() {
    this.fullName="empty";
    this.uuid = UUID.randomUUID();
    }
// Constructor 1 - new resume for new fullname, UUID created randomly (type 4) and automatically
    public Resume(String fullName) {
        this.fullName=fullName;
        this.uuid = UUID.randomUUID();
    }
// One more constructor for methods like update
    public Resume(UUID uuid, String fullName) {
        this.fullName=fullName;
        this.uuid = uuid;
    }
// Constructor 3 - for null fullName field avoid
    public Resume(UUID uuid)  {
    this.fullName="empty";
    this.uuid = uuid;
    }
    // Resume getter
    public UUID   getUuid() { return uuid; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid,resume.uuid)&&
               Objects.equals(fullName,resume.fullName);
    }
    @Override
    public int hashCode() {
        return Objects.hash(uuid,fullName);
    }
    @Override
    public String toString() {
        return " UUID:"+uuid+" fullName:"+fullName;
    }
// https://habrahabr.ru/post/247015/
    @Override
    public int compareTo(Resume o) {
        int result;
        result=uuid.compareTo(o.uuid);
        if(result != 0) return result;
//   For future usage
//      result=fullName.compareTo(o.fullName);
//      if(result != 0) return result;
        return 0;
    }
}
