package webapp.storage;

import org.junit.Test;
import webapp.exceptions.StorageException;
import webapp.model.Resume;

import static junit.framework.TestCase.assertTrue;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest{
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }
    @Test
    public void Tst12_ArrayIndexOutOfBoundsExceptionCheck() throws StorageException {
        boolean thrown = false;
        System.out.println("--Tst12_array_Index_Out_Of_Bounds_Exception+Check---");
        try {
             for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT+1; i++) {
                 storage.save(new Resume("One more name " + i));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
             thrown = true;
             System.out.println("ArrayIndexOutOfBoundsException exception detected!");
        }
        assertTrue(thrown);
    }
}