package webapp.storage;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import webapp.exceptions.StorageException;
import webapp.model.*;
import webapp.util.Config;

import java.io.File;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static webapp.model.TypeOfContact.*;
import static webapp.model.TypeOfSection.*;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    protected Resume r;
    // Depth of test data and length of test string
    static int ArrayLengthLimit = 5;
    // Array for unit testing
    static Resume[] testArray = new Resume[ArrayLengthLimit];

    private static String createRandomString(int codeLength) {
        final String id = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        return new SecureRandom()
                .ints(codeLength, 0, id.length())
                .mapToObj(id::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    private static java.util.ArrayList newSubList(List originalList) {
        return new ArrayList<String>(originalList.subList(0, 3));
    }

    @BeforeClass

    public static void setUpOnce() {
        Random generator = new Random();
        final List<String> listOfAchivements = new ArrayList<>(Arrays.asList("Achievement1", "Achievement2", "Achievement3", "Achievement4", "Achievement5", "Achievement6"));
        final List<String> listOfPositions = new ArrayList<>(Arrays.asList("SW Developer", "Team Lead", "Sr SW Developer", "Jr SW Developer", "Solution Architect", "Project manager"));
        final List<String> listOfPosDescr = new ArrayList<>(Arrays.asList("Description1", "Description2", "Description3", "Description4", "Description5", "Description6"));
        final List<String> listOfOrganizationsName = new ArrayList<>(Arrays.asList("Circus", "Zoo", "Hospital", "Museum", "Barbarian tribe", "Academy"));
        final List<String> listOfEducationName = new ArrayList<>(Arrays.asList("University", "College", "Trade school", "Labor Camp", "Home education", "Street education"));
        final List<String> listOfQualifications = new ArrayList<>(Arrays.asList("Java", "MPLS", "Networking", "Project management", "Python", "Risk management", "IP Routing Protocols", "UML",
                "Jira", "Confluence", "System Integration", "BPMN", "Software Development", "Unit Testing", "Android"));

        for (int i = 0; i < ArrayLengthLimit; i++) {

            Collections.shuffle(listOfQualifications);
            Collections.shuffle(listOfAchivements);
            Collections.shuffle(listOfPositions);
            Collections.shuffle(listOfPosDescr);
            Collections.shuffle(listOfOrganizationsName);
            Collections.shuffle(listOfEducationName);
            LocalDate randomDateBegin = LocalDate.now().minusDays(generator.nextInt(10000) + 365);
            LocalDate randomDateEnd = randomDateBegin.plusDays(generator.nextInt(1095));
            int stringLength = 20;
            testArray[i] = new Resume(createRandomString(stringLength));
            testArray[i].setContact(SKYPE, createRandomString(stringLength));
            testArray[i].setContact(HOMEPHONE, "+8 495-" + Integer.toString(generator.nextInt(8999999) + 1000000));
            testArray[i].setContact(MOBILEPHONE, "+7 095-" + Integer.toString(generator.nextInt(8999999) + 1000000));
            testArray[i].setSection(OBJECTIVE, new StringSection(createRandomString(stringLength)));
            testArray[i].setSection(PERSONAL, new StringSection(createRandomString(stringLength)));
            testArray[i].setSection(ACHIEVEMENT, new ListSection(newSubList(listOfAchivements)));
            testArray[i].setSection(QUALIFICATIONS, new ListSection(newSubList(listOfQualifications)));
            testArray[i].setSection(EXPERIENCE, new OrganizationSection(new Organization(listOfOrganizationsName.get(0), createRandomString(stringLength), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(0), listOfPosDescr.get(0)),
                    new Position(randomDateBegin, randomDateEnd, listOfPositions.get(1), listOfPosDescr.get(1)), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(2), listOfPosDescr.get(2))),
                    new Organization(listOfOrganizationsName.get(1), createRandomString(stringLength), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(1), listOfPosDescr.get(1)),
                            new Position(randomDateBegin, randomDateEnd, listOfPositions.get(2), listOfPosDescr.get(2)), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(3), listOfPosDescr.get(3))),
                    new Organization(listOfOrganizationsName.get(2), createRandomString(stringLength), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(2), listOfPosDescr.get(2)),
                            new Position(randomDateBegin, randomDateEnd, listOfPositions.get(3), listOfPosDescr.get(3)), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(4), listOfPosDescr.get(4)))));
            testArray[i].setSection(EDUCATION, new OrganizationSection(new Organization(listOfEducationName.get(0), createRandomString(stringLength), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(0), listOfPosDescr.get(0)),
                    new Position(randomDateBegin, randomDateEnd, listOfPositions.get(1), listOfPosDescr.get(1)), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(2), listOfPosDescr.get(2))),
                    new Organization(listOfEducationName.get(1), createRandomString(stringLength), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(1), listOfPosDescr.get(1)),
                            new Position(randomDateBegin, randomDateEnd, listOfPositions.get(2), listOfPosDescr.get(2)), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(3), listOfPosDescr.get(3))),
                    new Organization(listOfEducationName.get(2), createRandomString(stringLength), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(2), listOfPosDescr.get(2)),
                            new Position(randomDateBegin, randomDateEnd, listOfPositions.get(3), listOfPosDescr.get(3)), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(4), listOfPosDescr.get(4)))));
        }
    }

    @Before
    public void setUp() {
        // -------Fill storage from testArray ------- */
        if (storage.size() > 0) {
            storage.clear();
        }
        Arrays.stream(testArray).forEach(storage::save);
    }

    @Test
    public void storageClearTest() throws StorageException {
        // --------Storage method CLEAR test --------*/
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void storageDeleteTest() throws StorageException {
        // ------- Storage method DELETE test --------*/
        for (int i = 0; i < storage.size(); i++) {
            int j = storage.size() - 1;
            storage.delete(testArray[i].getUuid());
            assertEquals(j, storage.size());
        }
    }

    @Test
    public void storageGetAllSortedTest() throws StorageException {
        // ------- Storage method GETALLSORTED test --------*/
        List<Resume> listFromStorage = storage.getAllSorted();
        Collections.sort(Arrays.asList(testArray));
        assertEquals(storage.size(), listFromStorage.size());
        assertEquals(Arrays.asList(testArray), listFromStorage);
    }

    @Test
    public void storageSaveTest() throws StorageException {
        // ------- Storage method SAVE test ----------------*/
        UUID uuid = testArray[0].getUuid();
        storage.delete(uuid);
        int i = storage.size();
        storage.save(testArray[0]);
        assertEquals(i + 1, storage.size());
        assertEquals(testArray[0], storage.get(uuid));
    }

    @Test
    public void storageSizeTest() throws StorageException {
        // ------- Storage method SIZE test -----------------*/
        assertEquals(ArrayLengthLimit, storage.size());
    }

    @Test
    public void storageGetTest() throws StorageException {
        // ------- Storage method GET test-------------------*/
        for (int i = 0; i < storage.size(); i++) {
            assertEquals(testArray[i], storage.get(testArray[i].getUuid()));
        }
    }

    @Test
    public void storageUpdateTest() throws StorageException {
        // ------- Storage method UPDATE test-----------------*/
        UUID uuid = testArray[0].getUuid();
        testArray[0].setFullName("petroff");
        testArray[0].setContact(TypeOfContact.HOMEPHONE,"+ 7 777 7777777");
        if (storage.size() != 0) {
            storage.update(uuid, testArray[0] );
            assertEquals(testArray[0],storage.get(uuid));
        } else System.out.println("Storage size = 0! No update!");
    }

    @Test(expected = StorageException.class)
    public void exceptionGetNotExistSKFromStorageTest() {
        // --Exception: get not existed Search Key from storage test ------*/
        UUID wrongUUID = UUID.randomUUID();
        storage.get(wrongUUID);
    }

    @Test(expected = StorageException.class)
    public void exceptionDeleteNotExistedSKFromStorageTest() {
        // --Exception: delete not existed Search Key from storage test ---*/
        UUID wrongUUID = UUID.randomUUID();
        storage.delete(wrongUUID);
    }

    @Test(expected = StorageException.class)
    public void exceptionUpdateNotExistSKInStorageTest() {
        // --Exception: update not existed Search Key in storage test -----*/
        UUID wrongUUID = UUID.randomUUID();
        storage.get(wrongUUID);
    }

    @Test(expected = StorageException.class)
    public void exceptionSaveAlreadyExistedSKInStorageTest() {
        // --Exception: save already existed Search Key in storage test ---*/
        if (storage.size() > 0) storage.save(testArray[0]);
        else System.out.println("Storage length = 0!");
    }

    @Test(expected = StorageException.class)
    public void exceptionDeleteInEmptyStorageTest() {
        // --Exception: try to delete in empty storage test ---------------*/
        storage.clear();
        storage.delete(UUID.randomUUID());
    }

    @Test(expected = StorageException.class)
    public void exceptionGetInEmptyStorageTest() {
        // --Exception: get in empty storage test -------------------------*/
        storage.clear();
        storage.get(UUID.randomUUID());
    }

}