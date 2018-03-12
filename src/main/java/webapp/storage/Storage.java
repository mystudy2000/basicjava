package webapp.storage;

import webapp.model.Resume;

import java.util.List;
import java.util.UUID;

public interface Storage {

    void     clear();
    void     update(UUID uuid, Resume r);
    void     save(Resume r);
    Resume   get(UUID uuid);
    void     delete(UUID uuid);
    List<Resume> getAllSorted();
    int      size();
}
