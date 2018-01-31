package webapp.storage;

import webapp.exceptions.StorageException;
import webapp.model.Resume;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class AbstractArrayStorage extends AbstractStorage {
    static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected int size = 0;

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume r, int index);

    public int size() { return size; }

    public List<Resume> doGetAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    public void clear(){
          Arrays.fill(storage, 0, size, null);
          size = 0;
    }

    @Override
    protected void doSave(Resume r, Object SK) throws StorageException {
        StorageOverflowCheck();
        insertElement(r, (Integer)SK);
        size++;
    }

    @Override
    protected void doDelete(Object index) {
        fillDeletedElement((Integer)index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Resume doGet(Object index) {
        return storage[(Integer)index];
    }

    @Override
    protected boolean isExist(Object index) { return (Integer)index>=0; }

    @Override
    protected void doUpdate( Object index, Resume r){
        storage[(Integer) index]=r;
    }

    @Override
    protected Integer getSearchKey(UUID uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    private void StorageOverflowCheck() throws StorageException {
        if (size==STORAGE_LIMIT) {
            throw new StorageException("Storage is full!",1000);}
    }
}

