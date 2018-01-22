package webapp.storage;

import org.apache.commons.text.RandomStringGenerator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import webapp.exceptions.StorageException;
import webapp.model.Resume;

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
    public void Tst01_clear_OK() {
        System.out.println("----------Tst01_method_clear_check--------");
        System.out.println("Size before clearing:"+storage.size());
        storage.clear();
        assertEquals(0, storage.size());
        System.out.println("Size after clearing:"+storage.size());
    }
    @Test
    public void Tst02_delete_OK() throws StorageException {
        System.out.println("----------Tst02_method_delete_check--------");
        for (int i = 0; i<storage.size(); i++) {
            int j=storage.size()-1;
            storage.delete(testArray[i].getUuid());
            assertEquals(j, storage.size());
        }
    }
    @Test
    public void Tst03_getAll_OK(){
        System.out.println("----------Tst03_method_getAll_check--------");
        Resume[] arrayFromStorage = storage.getAll();
        assertEquals(storage.size(), arrayFromStorage.length);
        for (int i = 0; i < storage.size(); i++) {
            System.out.println(arrayFromStorage[i].toString());
        }
    }
    @Test
    public void Tst04_save_OK() throws StorageException {
        System.out.println("----------Tst04_method_save_check--------");
        r=new Resume("ivanoff");
        System.out.println("Old storage size:"+storage.size());
        int i = storage.size();
        storage.save(r);
        assertEquals(i+1, storage.size());
        System.out.println("Stored resume with UUID:"+r.getUuid());
        System.out.println("New storage size:"+storage.size());
    }
    @Test
    public void Tst05_size_OK() {
        System.out.println("----------Tst05_method_size_check--------");
        assertEquals(ArrayLengthLimit, storage.size());
    }
    @Test
    public void Tst06_get_OK() throws StorageException {
        System.out.println("----------Tst06_method_get_check--------");
        for (int i = 0; i < storage.size(); i++) {
            assertEquals(testArray[i],storage.get(testArray[i].getUuid()));
        }
    }
    @Test
    public void Tst07_update_OK() throws StorageException {
        System.out.println("----------Tst07_method_update_check--------");
        r=new Resume();
            r.uuid=testArray[0].getUuid();
            r.fullName="petroff";
         if (storage.size()!=0) {
            storage.update(r.uuid,r);
            assertEquals(r,storage.get(r.uuid));}
            else System.out.println("Storage length = 0! No update!");
        }
    @Test(expected = StorageException.class)
    public void Tst08_getNotExistUuidFromStorageCheck() throws Exception {
        System.out.println("--Tst08_get_Not_Existed_Uuid_From_Storage_Check---");
        UUID wrongUUID = UUID.randomUUID();
        storage.get(wrongUUID);
    }
    @Test(expected = StorageException.class)
    public void Tst09_deleteNotExistUuidFromStorageCheck() throws Exception {
        System.out.println("--Tst09_delete_Not_Existed_Uuid_From_Storage_Check---");
        UUID wrongUUID = UUID.randomUUID();
        storage.delete(wrongUUID);
    }
    @Test(expected = StorageException.class)
    public void Tst10_updateNotExistUuidInStorageCheck() throws Exception {
        System.out.println("--Tst10_update_Not_Existed_Uuid_In_Storage_Check---");
        UUID wrongUUID = UUID.randomUUID();
        storage.get(wrongUUID);
    }
    @Test(expected = StorageException.class)
    public void Tst11_saveAlreadyExistUuidInStorageCheck() throws Exception {
        System.out.println("--Tst11_save_Already_Existed_Uuid_In_Storage_Check---");
        if (storage.size()>0) storage.save(testArray[0]);
        else System.out.println("Storage length = 0! No exception!");
    }
}