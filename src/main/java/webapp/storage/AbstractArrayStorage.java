package webapp.storage;

import webapp.exceptions.DuplicateException;
import webapp.exceptions.NotFoundException;
import webapp.exceptions.StorageException;
import webapp.exceptions.EmptyException;
import webapp.model.Resume;

import java.util.Arrays;
import java.util.UUID;

public abstract class AbstractArrayStorage implements Storage {
      static final int STORAGE_LIMIT = 10000;
      protected Resume[] storage = new Resume[STORAGE_LIMIT];
      protected int size = 0;
      protected abstract void fillDeletedElement(int index);
      protected abstract void insertElement(Resume r, int index);
      protected abstract int getIndex(UUID uuid);
      public int size() { return size; }

    public void clear() throws EmptyException {
          if ((size==0)|(storage==null)) throw new EmptyException("storage is empty", 705);
          else{Arrays.fill(storage, 0, size, null);
          size = 0;}
          }
    public void update(UUID uuid, String Full_Name) throws NotFoundException {
        int index = getIndex(uuid);
        if (index < 0) throw new NotFoundException("uuid is not found", 700);
        else {
            storage[index].fullName=Full_Name;
            }
        }
    public void save(Resume r) throws StorageException {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            throw new DuplicateException("uuid already exist", 701);
            } else if (size == STORAGE_LIMIT) throw new StorageException("storage overflow", 702);
        else {
            insertElement(r, index);
            size=size+1;
        }
        }
    public Resume get(UUID uuid) throws NotFoundException {
            int index = getIndex(uuid);
            if (index < 0) {
                throw new NotFoundException("uuid is not found", 703);
            } else {
                return storage[index];
            }
        }
    public void delete(UUID uuid) throws NotFoundException {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotFoundException("uuid is not found", 704);
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
