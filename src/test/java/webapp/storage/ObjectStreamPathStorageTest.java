package webapp.storage;

import webapp.storage.serialization.ObjectStreamStrategy;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamStrategy()));
    }

}