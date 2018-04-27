package webapp.storage;

import webapp.exceptions.StorageException;
import webapp.model.Resume;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    static final int STORAGE_LIMIT = 1000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected int size = 0;

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume r, int index);

    public int size() {
        return size;
    }

    public List<Resume> doGetAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doSave(Resume r, Integer SK) {
        StorageOverflowCheck();
        insertElement(r, SK);
        size++;
    }

    @Override
    protected void doDelete(Integer SK) {
        fillDeletedElement(SK);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Resume doGet(Integer SK) {
        return storage[SK];
    }

    @Override
    protected boolean isExist(Integer SK) {
        return SK >= 0;
    }

    @Override
    protected void doUpdate(Integer SK, Resume r) {
        storage[SK] = r;
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

    private void StorageOverflowCheck() {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage is full! Size =" + size());
        }
    }
}

