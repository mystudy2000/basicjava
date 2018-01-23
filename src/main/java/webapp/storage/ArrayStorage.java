package webapp.storage;
import webapp.model.Resume;

/*** Array based storage for Resumes */
    public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void doSave(Resume r) {
        insertElement(r, size);
        size++;
    }

    @Override
    protected void fillDeletedElement(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected void insertElement(Resume r, int index) {
        storage[size] = r;
    }

}


