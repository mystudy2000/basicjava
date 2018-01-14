package webapp.storage;
import webapp.exceptions.NotFoundException;
import webapp.exceptions.StorageException;
import webapp.exceptions.EmptyException;
import webapp.model.Resume;
import java.util.UUID;

public interface Storage {

    void clear() throws EmptyException;;
    void update(UUID uuid, String fullName) throws NotFoundException;
    void save(Resume r) throws StorageException;
    Resume get(UUID uuid) throws NotFoundException;
    void delete(UUID uuid) throws NotFoundException;
    Resume[] getAll();
    int size();
}
