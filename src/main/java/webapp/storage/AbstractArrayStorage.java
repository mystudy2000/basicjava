package webapp.storage;
import webapp.exceptions.ExistStorageException;
import webapp.exceptions.NotExistStorageException;
import webapp.exceptions.StorageException;
import webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
      private static final int STORAGE_LIMIT = 10000;
      protected Resume[] storage = new Resume[STORAGE_LIMIT];
      protected int size = 0;
      protected abstract void fillDeletedElement(int index);
      protected abstract void insertElement(Resume r, int index);
      protected abstract int getIndex(String uuid);
      public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        }
    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
            } else {
            storage[index] = r;
            }
        }
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
            } else if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
            } else {
            insertElement(r, index);
            size++;
        }
        }
        public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index <0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];   }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
            } else {
            fillDeletedElement(index);
            storage[size - 1] = null;
            size--;
            }
        }
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
        }
 }
