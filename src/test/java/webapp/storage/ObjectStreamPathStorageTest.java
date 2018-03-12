package webapp.storage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new webapp.storage.ObjectStreamPathStorage(STORAGE_DIR.getAbsolutePath()));
    }

}