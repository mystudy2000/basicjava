package webapp.storage;

import org.junit.Test;
import webapp.exceptions.StorageException;
import webapp.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void exceptionStorageOverflowTest() throws StorageException {
        // --Exception: storage overflow test ---*/
        for (int i = ArrayLengthLimit; i < AbstractArrayStorage.STORAGE_LIMIT + 1; i++) {
            storage.save(new Resume("One more name " + i));
        }
    }
}
