package webapp;

import webapp.exceptions.StorageException;
import webapp.model.Resume;
import webapp.storage.ArrayStorage;

/*** Test ArrayStorage */
class MainTestArrayStorage {
    private static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) throws StorageException {
        Resume r1 = new Resume("Ivanoff");
        Resume r2 = new Resume("Petroff");
        Resume r3 = new Resume("Sidoroff");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.uuid));
        System.out.println("Get r2: " + ARRAY_STORAGE.get(r2.uuid));
        System.out.println("Get r3: " + ARRAY_STORAGE.get(r3.uuid));

        System.out.println("Size: " + ARRAY_STORAGE.size());

//        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.uuid);
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}