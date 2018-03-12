package webapp.storage;

import com.sun.istack.internal.Nullable;
import webapp.exceptions.StorageException;
import webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public abstract class AbstractStorage <SK> implements Storage{

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public abstract int size();

    protected abstract void doUpdate(SK SK, Resume r);

    protected abstract void doSave(Resume r, SK SK) throws StorageException;

    protected abstract Resume doGet(SK SK);

    protected abstract void doDelete(SK SK);

    protected abstract List<Resume> doGetAll();

    // CRUD methods
    /** Object searchKey (SK) type =
     *  if storage = set(array, sorted array, list) then @param SK = integer
     *  if storage = uuid.hasmap then @param SK = uuid
     *  if storage = resume.hashmap then @param SK = resume
     */
    public void update(UUID uuid, Resume r) throws StorageException {
        LOG.info("Update " + r);
        EmptyStorageCheck();
        SK searchKey = getSearchKeyIfExist(uuid);
        doUpdate(searchKey,r);
    }

    public void save(Resume r) throws StorageException {
//        LOG.info("Save " + r);
        SK searchKey = SearchKeyNotExistCheck(r.getUuid());
        doSave(r,searchKey);
    }

    public void delete(UUID uuid) throws StorageException {
        LOG.info("Delete " + uuid);
        EmptyStorageCheck();
        SK searchKey = getSearchKeyIfExist(uuid);
        doDelete(searchKey);
    }

    public Resume get(UUID uuid) throws StorageException {
        LOG.info("Get " + uuid);
        EmptyStorageCheck();
        SK searchKey = getSearchKeyIfExist(uuid);
        return doGet(searchKey);
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> resumeList = doGetAll();
        Collections.sort(resumeList);
        return resumeList;
    }

    @Nullable
    protected abstract SK getSearchKey(UUID uuid);
    protected abstract boolean isExist(SK SK);

    // Exception checks
    private SK getSearchKeyIfExist(UUID uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not found");
            throw new StorageException("Search key not found " + searchKey);}
        return searchKey;
    }

    private SK SearchKeyNotExistCheck(UUID uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey))  {
            LOG.warning("Resume " + uuid + " duplicated");
            throw new StorageException("Search key not found " + searchKey);
        }
        return searchKey;
    }

    private void EmptyStorageCheck() {
        if (size()==0) {
            LOG.warning("Storage is empty!");
            throw new StorageException("Storage is empty!");
        }
    }
}
