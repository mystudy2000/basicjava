package webapp.storage;

import webapp.exceptions.StorageException;
import webapp.model.Resume;

import java.util.List;
import java.util.UUID;

public interface Storage {

    void     clear();
    void     update(UUID uuid, Resume r) throws StorageException;
    void     save(Resume r) throws StorageException;
    Resume   get(UUID uuid) throws StorageException;
    void     delete(UUID uuid) throws StorageException;
    List<Resume> getAllSorted();
    int      size();
}
