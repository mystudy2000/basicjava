package webapp.storage;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import webapp.exceptions.StorageException;
import webapp.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static webapp.model.TypeOfContact.*;
import static webapp.model.TypeOfSection.*;

public abstract class AbstractStorageTest {

    Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    protected Resume r;
    // Depth of test data and length of test string
    static int ArrayLengthLimit = 10;
    static int stringLength = 20;
    // Array for unit testing
    static Resume[] testArray = new Resume[ArrayLengthLimit];


    @BeforeClass

    public static void setUpOnce() {
        Random generator = new Random();
        final String alpha = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        final List<String> listOfAchivements = new ArrayList<>(Arrays.asList("Achievement1", "Achievement2", "Achievement3", "Achievement4", "Achievement5", "Achievement6"));
        final List<String> listOfPositions = new ArrayList<>(Arrays.asList("SW Developer", "Team Lead", "Sr SW Developer", "Jr SW Developer", "Solution Architect", "Project manager"));
        final List<String> listOfPosDescr = new ArrayList<>(Arrays.asList("Description1", "Description2", "Description3", "Description4", "Description5", "Description6"));
        final List<String> listOfOrganizations = new ArrayList<>(Arrays.asList("Circus", "Zoo", "Hospital", "Museum", "Barbarian tribe", "Academy"));
        final List<String> listOfEducation = new ArrayList<>(Arrays.asList("University", "College", "Trade school", "Labor Camp", "Home education", "Street education"));
        final List<String> listOfQualifications = new ArrayList<>(Arrays.asList("Java", "MPLS", "Networking", "Project management", "Python", "Risk management", "IP Routing Protocols", "UML",
                "Jira", "Confluence", "System Integration", "BPMN", "Software Development", "Unit Testing", "Android"));

        for (int i = 0; i < ArrayLengthLimit; i++) {
            Collections.shuffle(listOfQualifications);
            Collections.shuffle(listOfAchivements);
            Collections.shuffle(listOfPositions);
            Collections.shuffle(listOfPosDescr);
            Collections.shuffle(listOfOrganizations);
            Collections.shuffle(listOfEducation);
            LocalDate randomDateBegin = LocalDate.now().minusDays(generator.nextInt(10000) + 365);
            LocalDate randomDateEnd = randomDateBegin.plusDays(generator.nextInt(1095));
            testArray[i] = new Resume(new Random().ints(stringLength, 0, alpha.length()).mapToObj(alpha::charAt).map(Object::toString).collect(Collectors.joining()));
            testArray[i].setContact(SKYPE, new Random().ints(stringLength, 0, alpha.length()).mapToObj(alpha::charAt).map(Object::toString).collect(Collectors.joining()));
            testArray[i].setContact(HOMEPHONE, "+8 495-" + Integer.toString(generator.nextInt(8999999) + 1000000));
            testArray[i].setContact(MOBILEPHONE, "+7 095-" + Integer.toString(generator.nextInt(8999999) + 1000000));
            testArray[i].setSection(OBJECTIVE, new StringSection(new Random().ints(stringLength, 0, alpha.length()).mapToObj(alpha::charAt).map(Object::toString).collect(Collectors.joining())));
            testArray[i].setSection(PERSONAL, new StringSection(new Random().ints(stringLength, 0, alpha.length()).mapToObj(alpha::charAt).map(Object::toString).collect(Collectors.joining())));
            testArray[i].setSection(ACHIEVEMENT, new ListSection(listOfAchivements.subList(0, 3)));
            testArray[i].setSection(QUALIFICATIONS, new ListSection(listOfQualifications.subList(0, 3)));
            testArray[i].setSection(EXPERIENCE, new OrganizationSection(new Organization(listOfOrganizations.get(0), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(0), listOfPosDescr.get(0)),
                    new Position(randomDateBegin, randomDateEnd, listOfPositions.get(1), listOfPosDescr.get(1)), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(2), listOfPosDescr.get(2))),
                    new Organization(listOfOrganizations.get(1), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(1), listOfPosDescr.get(1)),
                            new Position(randomDateBegin, randomDateEnd, listOfPositions.get(2), listOfPosDescr.get(2)), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(3), listOfPosDescr.get(3))),
                    new Organization(listOfOrganizations.get(2), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(2), listOfPosDescr.get(2)),
                            new Position(randomDateBegin, randomDateEnd, listOfPositions.get(3), listOfPosDescr.get(3)), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(4), listOfPosDescr.get(4)))));
            testArray[i].setSection(EDUCATION, new OrganizationSection(new Organization(listOfEducation.get(0), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(0), listOfPosDescr.get(0)),
                    new Position(randomDateBegin, randomDateEnd, listOfPositions.get(1), listOfPosDescr.get(1)), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(2), listOfPosDescr.get(2))),
                    new Organization(listOfEducation.get(1), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(1), listOfPosDescr.get(1)),
                            new Position(randomDateBegin, randomDateEnd, listOfPositions.get(2), listOfPosDescr.get(2)), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(3), listOfPosDescr.get(3))),
                    new Organization(listOfEducation.get(2), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(2), listOfPosDescr.get(2)),
                            new Position(randomDateBegin, randomDateEnd, listOfPositions.get(3), listOfPosDescr.get(3)), new Position(randomDateBegin, randomDateEnd, listOfPositions.get(4), listOfPosDescr.get(4)))));
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
    public void storageClearTest() throws StorageException {
        System.out.println("--------Storage method CLEAR test --------");
        System.out.println("Size before clearing:" + storage.size());
        storage.clear();
        assertEquals(0, storage.size());
        System.out.println("Size after clearing:" + storage.size());
    }

    @Test
    public void storageDeleteTest() throws StorageException {
        System.out.println("------- Storage method DELETE test --------");
        for (int i = 0; i < storage.size(); i++) {
            int j = storage.size() - 1;
            storage.delete(testArray[i].getUuid());
            assertEquals(j, storage.size());
        }
    }

    @Test
    public void storageGetAllSortedTest() throws StorageException {
        System.out.println("------- Storage method GETALLSORTED test --------");
        List<Resume> ListFromStorage = storage.getAllSorted();
        assertEquals(storage.size(), ListFromStorage.size());
        for (Resume r : ListFromStorage) {
            System.out.print(r.toString());
        }
    }

    @Test
    public void storageSaveTest() throws StorageException {
        System.out.println("------- Storage method SAVE test --------");
        r = new Resume("ivanoff");
        System.out.println("Old storage size:" + storage.size());
        int i = storage.size();
        storage.save(r);
        assertEquals(i + 1, storage.size());
        System.out.println("Stored resume with UUID:" + r.getUuid());
        System.out.println("New storage size:" + storage.size());
    }

    @Test
    public void storageSizeTest() throws StorageException {
        System.out.println("------- Storage method SIZE test --------");
        assertEquals(ArrayLengthLimit, storage.size());
    }

    @Test
    public void storageGetTest() throws StorageException {
        System.out.println("------- Storage method GET test--------");
        for (int i = 0; i < storage.size(); i++) {
            assertEquals(testArray[i], storage.get(testArray[i].getUuid()));
        }
    }

    @Test
    public void storageUpdateTest() throws StorageException {
        System.out.println("------- Storage method UPDATE test--------");
        UUID uuid = testArray[0].getUuid();
        String fullName = "petroff";
        r = new Resume(uuid, fullName);
        if (storage.size() != 0) {
            storage.update(r.uuid, r);
            assertEquals(r, storage.get(r.uuid));
        } else System.out.println("Storage length = 0! No update!");
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
        if (storage.size() > 0) storage.save(testArray[0]);
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