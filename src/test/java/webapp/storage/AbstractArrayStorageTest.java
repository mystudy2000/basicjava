package webapp.storage;

import org.apache.commons.text.RandomStringGenerator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import webapp.exceptions.EmptyException;
import webapp.exceptions.NotFoundException;
import webapp.exceptions.StorageException;
import webapp.model.Resume;

import java.util.UUID;

import static org.junit.Assert.assertEquals;


public class AbstractArrayStorageTest {

    public Storage storage = new ArrayStorage();
    Resume r;
    // constructor 1
     public AbstractArrayStorageTest() {}
    // constructor 2
    AbstractArrayStorageTest(Storage storage) {this.storage = storage;}
    // Length of test array and strings
    static int ArrayLengthLimit = 6;
    static int StringLenghtLimit = 10;
    // Arrays for tests
    static String[] fullNameArray = new String[ArrayLengthLimit];
    static UUID[] testUUID = new UUID [ArrayLengthLimit];

    @BeforeClass
    static public void setUpOnce() {
        // Fill test array by random fullNames
        for (int i = 0; i < ArrayLengthLimit; i++) {
            fullNameArray[i] = new RandomStringGenerator.Builder().withinRange('a', 'z').build()
                    .generate(StringLenghtLimit);
        }

    }
    @Before
    public void setUp() throws Exception {
        // Fill resume storage
        for (int i = 0; i < ArrayLengthLimit-1; i++) {
            r = new Resume(fullNameArray[i]);
            storage.save(r);
            testUUID[i] = r.getUuid();
//            System.out.print("Full Name: " + fullNameArray[i]);
//            System.out.println(" UUID: " + testUUID[i]);
        }
    }
    @Test
    public void clear() throws EmptyException {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void size() {
        assertSize(ArrayLengthLimit-1);
    }

    @Test
    public void update() throws NotFoundException {
        r.uuid=testUUID[1];
        r.fullName="petroff";
        storage.update(r.uuid,r.fullName);
        assertEquals(r,storage.get(testUUID[1]));
    }

    @Test
    public void save() throws StorageException {
        r=new Resume("ivanoff");
        storage.save(r);
        assertSize(ArrayLengthLimit);
        assertGet(r.getUuid());
    }
    @Test
    public void get() throws NotFoundException {
        for (int i = 0; i < ArrayLengthLimit - 1; i++) {
            assertGet(testUUID[i]);
        }
    }
    @Test
    public void delete() throws NotFoundException {
        for (int i = ArrayLengthLimit - 1; i==0; i--) {
            storage.delete(testUUID[i]);
            assertSize(i);
        }
    }
    @Test
    public void getAll() {
        Resume[] array = storage.getAll();
        assertEquals(ArrayLengthLimit-1, array.length);
    }
//  Tests for exceptions
    @Test(expected = NotFoundException.class)
    public void del_not_exist() throws Exception {
        storage.delete(testUUID[1]);
        storage.delete(testUUID[1]);
    }
    @Test(expected = NotFoundException.class)
    public void get_not_exist() throws Exception {
        storage.delete(testUUID[1]);
        storage.get(testUUID[1]);
    }
    @Test(expected = NotFoundException.class)
    public void upd_not_exist() throws Exception {
        storage.delete(testUUID[1]);
        storage.update(testUUID[1],"sidoroff");
    }
    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        try {
            for (int i = ArrayLengthLimit; i <= AbstractArrayStorage.STORAGE_LIMIT+1; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            System.out.println("STORAGE OVERFLOW!");
        }
        storage.save(new Resume());
    }
//  Банальное сравнение
    private void assertGet(UUID uuid) throws NotFoundException {
        r=storage.get(uuid);
        assertEquals(uuid,r.uuid);
    }
    private void assertSize (int size) {
        assertEquals(size, storage.size());
    }
}

