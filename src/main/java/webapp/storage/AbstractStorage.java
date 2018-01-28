package webapp.storage;

import com.sun.istack.internal.Nullable;
import webapp.exceptions.StorageException;
import webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class AbstractStorage implements Storage{

    public abstract int size();

    protected abstract void doUpdate(Object SK, Resume r);

    protected abstract void doSave(Resume r) throws StorageException;

    protected abstract Resume doGet(Object SK);

    protected abstract void doDelete(Object SK);

    protected abstract List<Resume> doGetAll();

    // CRUD methods
    /** Object searchKey (SK) type =
     *  if storage = set(array, sorted array, list) then @param SK = integer
     *  if storage = uuid.hasmap then @param SK = uuid
     *  if storage = resume.hashmap then @param SK = resume
     */
    public void update(UUID uuid, Resume r) throws StorageException {
        EmptyStorageCheck();
        Object searchKey = getSearchKeyIfExist(uuid);
        doUpdate(searchKey,r);
    }

    public void save(Resume r) throws StorageException {
        SeachKeyNotExistCheck(r.getUuid());
        doSave(r);
    }

    public void delete(UUID uuid) throws StorageException {
        EmptyStorageCheck();
        Object searchKey = getSearchKeyIfExist(uuid);
        doDelete(searchKey);
    }

    public Resume get(UUID uuid) throws StorageException {
        EmptyStorageCheck();
        Object searchKey = getSearchKeyIfExist(uuid);
        return doGet(searchKey);
    }

    public List<Resume> getAllSorted() {
        List<Resume> resumeList = doGetAll();
        Collections.sort(resumeList);
        return resumeList;
    }

    @Nullable
    protected abstract Object getSearchKey(UUID uuid);
    protected abstract boolean isExist(Object SK);

    // Exception checks
    private Object getSearchKeyIfExist(UUID uuid) throws StorageException {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {throw new StorageException("Search key not found",700);}
        return searchKey;
        }

    private void SeachKeyNotExistCheck(UUID uuid) throws StorageException {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey))  throw new StorageException("Search key duplicated",800);}

    private void EmptyStorageCheck() throws StorageException {
        if (size()==0) throw new StorageException("Storage is empty!",900);
    }
}
