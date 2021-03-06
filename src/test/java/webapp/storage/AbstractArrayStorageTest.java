package webapp.storage;

import org.junit.Assert;
import org.junit.Test;
import webapp.exceptions.StorageException;
import webapp.model.Resume;

import static webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void exceptionStorageOverflowTest() throws StorageException {
        // --Exception: storage overflow test ---*/
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume("One more name " + i));
            }
        }
        catch (StorageException e) {
            Assert.fail();
        }
        storage.save(new Resume("This is overflow!!!"));
    }
}
