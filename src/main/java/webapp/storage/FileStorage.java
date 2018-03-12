package webapp.storage;

import webapp.exceptions.StorageException;
import webapp.model.Resume;
import webapp.storage.serialization.SerializationStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class FileStorage extends AbstractStorage<File> {
    private File dir;
    private SerializationStrategy serializationStrategy;

    FileStorage(File directory, SerializationStrategy serializationStrategy) {
        Objects.requireNonNull(directory, "directory must not be null");

        this.dir = directory;
        this.serializationStrategy = serializationStrategy;

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory format");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable or writable");
        }

    }

    @Override
    public int size() {
        File[] list = dir.listFiles();
        if (list == null) {
            throw new StorageException("SIZE: Directory " + dir.toString() + " does not exist");
        }
        return list.length;
    }


    @Override
    public void clear() throws StorageException {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                doDelete(file);
            }
        }
    }

    @Override
    protected File getSearchKey(UUID uuid) {
        return new File(dir, uuid.toString());
    }

    @Override
    protected void doUpdate(File file, Resume r) {
        try {
            serializationStrategy.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("doUpdate: File read error " + file.getName(), e);
        }
    }


    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("doSave: Couldn't create file " + file.getAbsolutePath(), e);
        }
        doUpdate(file, r);
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return serializationStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("doGet: File read error " + file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (file.isDirectory()) {
            throw new StorageException("doDelete: File " + file + " is directory");
        } else if (!file.exists()) {
            throw new StorageException("doDelete: File " + file + " is not exist");
        } else if (!file.delete()) {
            throw new StorageException("doDelete: File delete error " + file);
        }
    }

    protected List<Resume> doGetAll() {
        File[] files = dir.listFiles();
        if (files == null) {
            throw new StorageException("doGetAll: Directory " + dir.toString() + " is not exist");
        }
        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) {
            list.add(doGet(file));
        }
        return list;
    }
}