package webapp.model;
/*** Resume class */
public class Resume {

    // Unique identifier
public  String uuid;
public String getUuid() {return uuid; }
public void   setUuid(String uuid) {this.uuid = uuid;}
    @Override
    public String toString() {
        return uuid;
    }
}
