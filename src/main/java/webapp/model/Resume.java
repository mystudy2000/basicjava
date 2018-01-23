package webapp.model;

import java.util.Objects;
import java.util.UUID;

/*** Resume class */
public class Resume  {
/**  Resume = structure of
             uuid     UUID
             fullName string
 */
    public  UUID   uuid;
    public  String fullName;

    public Resume(String fullName) {
        this (UUID.randomUUID(), fullName);
    }

    public Resume(UUID uuid, String fullName) {
        this.fullName=fullName;
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
}
