package webapp.storage;

import webapp.exceptions.StorageException;
import webapp.model.Resume;

import java.io.*;
import java.util.logging.Logger;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    protected ObjectStreamPathStorage(String directory) {
        super(directory);
    }
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    protected void doWrite(Resume r, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            LOG.info("doWrite ");
            oos.writeObject(r);
        }
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            LOG.info("doRead ");
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume");
        }
    }
}
