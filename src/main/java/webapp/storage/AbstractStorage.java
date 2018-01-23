package webapp.storage;

import webapp.exceptions.StorageException;
import webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class AbstractStorage implements Storage{

    public abstract int size();

    protected abstract void doUpdate(Resume r, UUID uuid);

    protected abstract void doSave(Resume r);

    protected abstract Resume doGet(UUID uuid);

    protected abstract void doDelete(UUID uuid);

    protected abstract List<Resume> doGetAll();

    // CRUD methods
    public void update(UUID uuid, Resume r) throws StorageException {
        if (size()==0) throw new StorageException("Storage is empty!",900);
        SearchKeyNotExistCheck(uuid);
        doUpdate(r,uuid);
    }

    public void save(Resume r) throws StorageException {
        SearchKeyExistCheck(r.getUuid());
        doSave(r);
    }

    public void delete(UUID uuid) throws StorageException {
        EmptyStorageCheck();
        SearchKeyNotExistCheck(uuid);
        doDelete(uuid);
    }

    public Resume get(UUID uuid) throws StorageException {
        EmptyStorageCheck();
        SearchKeyNotExistCheck(uuid);
        return doGet(uuid);
    }

    public List<Resume> getAllSorted() {
        List<Resume> resumeList = doGetAll();
        Collections.sort(resumeList);
        return resumeList;
    }

    // Search methods
    protected abstract Object getSearchKey(UUID uuid);

    protected abstract boolean isExist(UUID uuid);

    // Exception checks

    void SearchKeyNotExistCheck(UUID uuid) throws StorageException {
        if (!isExist(uuid)) {
            throw new StorageException("Search key not found",700);
        }
    }

    void SearchKeyExistCheck(UUID uuid) throws StorageException {
        if (isExist(uuid)) {
            throw new StorageException("Search key duplicated",800);
        }
    }

    void EmptyStorageCheck() throws StorageException {
        if (size()==0) throw new StorageException("Storage is empty!",900);
    }
}
