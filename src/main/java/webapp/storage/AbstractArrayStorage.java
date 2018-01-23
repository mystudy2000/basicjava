package webapp.storage;

import webapp.exceptions.StorageException;
import webapp.model.Resume;

import java.util.Arrays;
import java.util.UUID;

public abstract class AbstractArrayStorage extends AbstractStorage {
    static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected int size = 0;

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume r, int index);

    public int size() { return size; }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void clear(){
          Arrays.fill(storage, 0, size, null);
          size = 0;
    }

    @Override
    public void save(Resume r) throws StorageException {
        StorageOverflowCheck();
        SearchKeyExistCheck(r.getUuid());
        doSave(r);
    }

    @Override
    public void doDelete(UUID uuid) {
        fillDeletedElement(getSearchKey(uuid));
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume doGet(UUID uuid) {
        return storage[getSearchKey(uuid)];
    }

    @Override
    protected boolean isExist(UUID uuid) { return getSearchKey(uuid) >= 0; }

    @Override
    public void doUpdate(Resume r, UUID uuid){
        int index = getSearchKey(uuid);
        storage[index]=r;
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

    void StorageOverflowCheck() throws StorageException {
        if (size==STORAGE_LIMIT) {
            throw new StorageException("Storage is full!",1000);}
    }
}

