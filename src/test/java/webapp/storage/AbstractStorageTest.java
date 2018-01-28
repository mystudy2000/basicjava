package webapp.storage;

import org.apache.commons.text.RandomStringGenerator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import webapp.exceptions.StorageException;
import webapp.model.Resume;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    Storage storage;
    protected AbstractStorageTest(Storage storage) {this.storage = storage;}
    protected Resume r;
    // Depth of test data and length of strings
    static int ArrayLengthLimit = 3;
    static int StringLengthLimit = 10;
    // Array for unit testing
    static Resume[] testArray =  new Resume[ArrayLengthLimit];

    @BeforeClass
    public static void setUpOnce() {
        System.out.println("Test array shown below:");
        for (int i = 0; i < ArrayLengthLimit; i++) {
            testArray[i] = new Resume(new RandomStringGenerator.Builder().withinRange('a', 'z').build().generate(StringLengthLimit));
            System.out.println(testArray[i].toString());
        }
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        System.out.println("Test array has been copied for one more test. ");
        for (int i = 0; i < ArrayLengthLimit; i++) {
            storage.save(testArray[i]);
        }
    }

    @Test
    public void storageClearTest() {
        System.out.println("--------Storage method CLEAR test --------");
        System.out.println("Size before clearing:"+storage.size());
        storage.clear();
        assertEquals(0, storage.size());
        System.out.println("Size after clearing:"+storage.size());
    }

    @Test
    public void storageDeleteTest() throws StorageException {
        System.out.println("------- Storage method DELETE test --------");
        for (int i = 0; i<storage.size(); i++) {
            int j=storage.size()-1;
            storage.delete(testArray[i].getUuid());
            assertEquals(j, storage.size());
        }
    }

    @Test
    public void storageGetAllSortedTest(){
        System.out.println("------- Storage method GETALLSORTED test --------");
        List<Resume> ListFromStorage = storage.getAllSorted();
        assertEquals(storage.size(), ListFromStorage.size());
        for (Resume r : ListFromStorage ) {System.out.print(r.toString());
        }
    }

    @Test
    public void storageSaveTest() throws StorageException {
        System.out.println("------- Storage method SAVE test --------");
        r=new Resume("ivanoff");
        System.out.println("Old storage size:"+storage.size());
        int i = storage.size();
        storage.save(r);
        assertEquals(i+1, storage.size());
        System.out.println("Stored resume with UUID:"+r.getUuid());
        System.out.println("New storage size:"+storage.size());
    }

    @Test
    public void storageSizeTest() {
        System.out.println("------- Storage method SIZE test --------");
        assertEquals(ArrayLengthLimit, storage.size());
    }

    @Test
    public void storageGetTest() throws StorageException {
        System.out.println("------- Storage method GET test--------");
        for (int i = 0; i < storage.size(); i++) {
            assertEquals(testArray[i],storage.get(testArray[i].getUuid()));
        }
    }

    @Test
    public void storageUpdateTest() throws StorageException {
        System.out.println("------- Storage method UPDATE test--------");
        UUID uuid=testArray[0].getUuid();
        String fullName="petroff";
        r=new Resume(uuid,fullName);
        if (storage.size()!=0) {
            storage.update(r.uuid,r);
            assertEquals(r,storage.get(r.uuid));}
            else System.out.println("Storage length = 0! No update!");
        }

    @Test(expected = StorageException.class)
    public void exceptionGetNotExistSKFromStorageTest() throws Exception {
        System.out.println("--Exception: get not existed Search Key from storage test ---");
        UUID wrongUUID = UUID.randomUUID();
        storage.get(wrongUUID);
    }

    @Test(expected = StorageException.class)
    public void exceptionDeleteNotExistedSKFromStorageTest() throws Exception {
        System.out.println("--Exception: delete not existed Search Key from storage test ---");
        UUID wrongUUID = UUID.randomUUID();
        storage.delete(wrongUUID);
    }

    @Test(expected = StorageException.class)
    public void exceptionUpdateNotExistSKInStorageTest() throws Exception {
        System.out.println("--Exception: update not existed Search Key in storage test ---");
        UUID wrongUUID = UUID.randomUUID();
        storage.get(wrongUUID);
    }

    @Test(expected = StorageException.class)
    public void exceptionSaveAlreadyExistedSKInStorageTest() throws Exception {
        System.out.println("--Exception: save already existed Search Key in storage test ---");
        if (storage.size()>0) storage.save(testArray[0]);
        else System.out.println("Storage length = 0!");
    }

    @Test(expected = StorageException.class)
    public void exceptionDeleteInEmptyStorageTest() throws Exception {
        System.out.println("--Exception: delete in empty storage test ---");
        storage.clear();
        storage.delete(UUID.randomUUID());
    }

    @Test(expected = StorageException.class)
    public void exceptionGetInEmptyStorageTest() throws Exception {
        System.out.println("--Exception: get in empty storage test ---");
        storage.clear();
        storage.get(UUID.randomUUID());
    }
}